package com.space.munovachattest.service;

import com.space.munovachattest.util.ChatApiClient;
import com.space.munovachattest.util.MetricsRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class BotExecutor {

    private final RSocketBotService bot;
    private final ChatApiClient chatApi;

    private final List<BotContext> activeBots = new CopyOnWriteArrayList<>();
    private final List<BotContext> activeStreams = new CopyOnWriteArrayList<>();

    public Mono<BotContext> createChat(Long memberId, String token, Long productId) {

        long createStart = System.nanoTime();

        // 1) 채팅방 생성
        return chatApi.createChatRoom(productId, token)
                .doOnError(e -> MetricsRegistry.recordChatCreateFail())
                .flatMap(chatId -> {
                    long ms = (System.nanoTime() - createStart);
                    // chatId를 받아서 connect 단계로 연결
                    return startBot(memberId, token, chatId, createStart);
                });
    }


    public Mono<BotContext> startBot(Long memberId, String token, Long chatId, long createStart) {
        AtomicBoolean firstAttempt = new AtomicBoolean(false);
        long connectStart = System.nanoTime();

        return bot.connect(token)
                        .doOnError(err -> MetricsRegistry.recordConnectFail())
                        .map(req -> new TempContext(req, chatId, memberId))
                .flatMap(temp -> {

                    long cs = (System.nanoTime() - connectStart);
                    MetricsRegistry.recordConnectSuccess(cs);

                    long joinStart = System.nanoTime();

                    return bot.join(temp.requester(), temp.memberId(), temp.chatId())
                            .doOnSuccess(v -> {
                                long ms = (System.nanoTime() - joinStart);
                                MetricsRegistry.recordJoinSuccess(ms);
                            })
                            .thenReturn(temp)                  // join 성공한 경우에만 req 반환
                            .onErrorResume(e -> {
                                MetricsRegistry.recordJoinFail();
                                return Mono.empty();         // join 실패 → stream/send 진행 안 함
                            });
                }).onErrorResume(e -> Mono.empty())
                .flatMap(temp -> {

                    // STREAM 구독
                    Disposable streamLoop = bot.stream(temp.requester(), temp.chatId(), temp.memberId())
                            .doOnNext(msg -> {
                                if ("SEND".equals(msg.getType())) {
                                    long recvAt = System.nanoTime();
                                    long latencyMs = (recvAt - msg.getTimestamp());
                                    MetricsRegistry.recordMessageReceived(latencyMs);
                                    if (firstAttempt.compareAndSet(false, true)) {
                                        MetricsRegistry.recordE2EStartup(recvAt - createStart);
                                    }
                                }
                            })
                            .doOnError(err -> System.out.println("STREAM ERROR " + err))
                            .subscribe();

                    // SEND LOOP 구독
                    Disposable sendLoop = Mono.delay(Duration.ofSeconds(1))
                            .thenMany(Flux.interval(Duration.ofSeconds(2)))
                            .flatMap(tick -> {
                                String content = "hello";
                                return bot.send(temp.requester(), temp.chatId(), temp.memberId(), content, System.nanoTime())
                                        .doOnSuccess(v -> MetricsRegistry.recordMessageSent())
                                        .doOnError(e -> MetricsRegistry.recordMessageError());
                            })
                            .subscribe();

                    // 완성된 BotContext 저장
                    BotContext context = new BotContext(
                            temp.requester(),
                            temp.chatId(),
                            temp.memberId(),
                            sendLoop,
                            streamLoop
                    );

                    activeBots.add(context);
                    return Mono.just(context);
                });
    }


    public void shutdownAll() {
        System.out.println("🛑 Shutting down all bots...");

        for (BotContext ctx : activeBots) {
            try { if (ctx.streamLoop() != null) ctx.streamLoop().dispose(); } catch (Exception ignored) {}
            try { if (ctx.sendLoop() != null) ctx.sendLoop().dispose(); } catch (Exception ignored) {}
            try { ctx.requester().rsocketClient().dispose(); } catch (Exception ignored) {}
        }

        activeBots.clear();
        activeStreams.clear();
    }


    // 임시 context (sendLoop/streamLoop 없음)
    private record TempContext(
            RSocketRequester requester,
            Long chatId,
            Long memberId
    ) {}

    // 최종 context (sendLoop/streamLoop 포함)
    public record BotContext(
            RSocketRequester requester,
            Long chatId,
            Long memberId,
            Disposable sendLoop,
            Disposable streamLoop
    ) {}

}