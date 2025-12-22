package com.space.munovachattest.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatApiClient {
    private final ObjectMapper mapper = new ObjectMapper();

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/api/chat")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();


    /**
     * 채팅방 생성 (Non-blocking, latency 측정 포함)
     *
     * @return Mono<Long> chatId
     */
    public Mono<Long> createChatRoom(Long productId, String token) {

        return Mono.defer(() -> {
            long start = System.nanoTime();
            return webClient.post()
                    .uri("/one-to-one/{id}", productId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(WebClientResponseException.class, ex -> {
                        log.error("❌ HTTP {} → {}", ex.getStatusCode(), ex.getResponseBodyAsString());
                        return Mono.error(ex);
                    })
                    .flatMap(body -> parseChatId(body))
                    .doOnSuccess(chatId -> {
                        long ms = System.nanoTime() - start;
                        MetricsRegistry.recordChatCreateLatency(ms);
                        log.info("🟢 Chat created → {} ({}ns)", chatId, ms);
                    });
        });
    }


    /**
     * JSON 파싱 로직을 분리하여 안정성 확보
     */
    private Mono<Long> parseChatId(String body) {
        try {
            JsonNode json = mapper.readTree(body);

            JsonNode dataNode = json.get("data");
            if (dataNode == null || dataNode.get("chatId") == null) {
                return Mono.error(new IllegalStateException("Invalid JSON: missing data.chatId"));
            }

            return Mono.just(dataNode.get("chatId").asLong());

        } catch (Exception e) {
            return Mono.error(new RuntimeException("JSON parse failed", e));
        }
    }
}
