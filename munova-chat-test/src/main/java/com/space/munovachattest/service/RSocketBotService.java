package com.space.munovachattest.service;

import com.space.munovachattest.model.ChatPayload;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.LocalDateTime;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class RSocketBotService {

    private final RSocketStrategies strategies;

    ///  CONNECT
    public Mono<RSocketRequester> connect(String token) {

        ConnectionProvider provider = ConnectionProvider.builder("rsocket-bot")
                .maxConnections(20000)
                .pendingAcquireMaxCount(50000)
                .build();

        HttpClient httpClient = HttpClient.create(provider)
                .host("localhost")
                .port(7070)
                .wiretap(true);

        WebsocketClientTransport transport =
                WebsocketClientTransport.create(httpClient, "ws//loac/rs");

        return Mono.fromSupplier(() ->
                RSocketRequester.builder()
                        .rsocketStrategies(strategies)
                        .setupMetadata(
                                token,
                                MimeTypeUtils.parseMimeType("message/x.rsocket.authentication.v0")
                        )
                        .transport(transport)
        ).doOnNext(r -> System.out.println("🟢 CONNECTED requester=" + r));
    }

    ///  JOIN
    public Mono<String> join(RSocketRequester requester, Long chatId, Long memberId) {

        return requester
                .route("join")
                .data(Map.of(
                        "type", "JOIN",
                        "chatId", chatId,
                        "senderId", memberId,
                        "content", "",
                        "timestamp", System.currentTimeMillis()
                ))
                .retrieveMono(String.class)
                .doOnNext(res -> System.out.println("JOIN OK → " + res))
                .doOnError(err -> System.out.println("JOIN ERROR " + err));
    }

    ///  STREAM
    public Flux<ChatPayload> stream(RSocketRequester requester, Long chatId, Long memberId) {

        return requester
                .route("chat.stream." + chatId)
                .data(memberId)
                .retrieveFlux(ChatPayload.class)
                .doOnNext(res -> System.out.println("📩STREAM OK on Service → " + res))
                .doOnError(err -> System.out.println("STREAM ERROR " + err));
    }

    ///  SEND
    public Mono<Void> send(RSocketRequester requester, Long chatId, Long memberId, String content, long sendAt) {

        return requester
                .route("chat.send")
                .data(Map.of(
                        "type", "SEND",
                        "chatId", chatId,
                        "senderId", memberId,
                        "content", content,
                        "timestamp", sendAt,
                        "createdAt", LocalDateTime.now()
                ))
                .send()
                .doOnSuccess(v -> System.out.println("SEND OK on Service"))
                .doOnError(err -> System.out.println("SEND ERROR → " + err));
    }

}
