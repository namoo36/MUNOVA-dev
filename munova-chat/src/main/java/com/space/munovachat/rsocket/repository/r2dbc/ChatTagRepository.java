package com.space.munovachat.rsocket.repository.r2dbc;

import com.space.munovachat.rsocket.entity.ChatTag;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatTagRepository extends ReactiveCrudRepository<ChatTag, Long> {

    Flux<ChatTag> findAllByChatId(Long chatId);

    Mono<Void> deleteByChatId(Long id);
}
