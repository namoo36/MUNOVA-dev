package com.space.munovachattest.service;


import com.space.munovachattest.util.AuthLoader;
import com.space.munovachattest.util.MetricsRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadScheduler implements CommandLineRunner {

    private final BotExecutor executor;

    @Override
    public void run(String... args) {

        long testStart = System.nanoTime();

        int TARGET = 2000;
        int PER_SECOND = 33;
        Long productId = 4L;
        int HOLD_TIME = 60;

        List<AuthLoader.AuthItem> authList = AuthLoader.load();

        System.out.println("🚀 Load Test Start: 매초 33명 생성 (총 " + TARGET + "명)");

        Flux<BotExecutor.BotContext> botFlux =
                Flux.range(0, TARGET)
                        .delayElements(Duration.ofMillis(1000 / PER_SECOND))
                        .flatMap(idx -> {

                            if (idx >= authList.size()) return Mono.empty();

                            var auth = authList.get(idx);

                            // BotContext 반환됨
                            return executor.createChat(auth.getMemberId(), auth.getToken(), productId)
                                    .onErrorResume(e -> Mono.empty());
                        });
        botFlux
                .collectList()   // 🔥 전체 bot 생성 완료를 기다림
                .flatMap(bots -> {

                    System.out.println("🔥 ALL BOT CREATED: " + bots.size());
                    System.out.println("⏳ HOLD " + HOLD_TIME + "초 유지...");

                    // 🔥 일정 시간 유지
                    return Mono.delay(Duration.ofSeconds(HOLD_TIME))
                            .thenReturn(bots);
                })
                .doOnNext(bots -> {
                    System.out.println("🛑 SHUTDOWN ALL BOTS (" + bots.size() + ")");

                    long testEnd = System.nanoTime();
                    executor.shutdownAll();

                    long elapsed = testEnd - testStart;
                    System.out.println("\n=======================================");
                    System.out.println("🛑 SHUTDOWN ALL BOTS (" + bots.size() + ")");
                    System.out.println("🕒 TEST TOTAL ELAPSED TIME:");
                    System.out.println("   → " + elapsed + "ns");
                    System.out.println("=======================================\n");
                    MetricsRegistry.printSummary();
                })
                .subscribe();
    }
}