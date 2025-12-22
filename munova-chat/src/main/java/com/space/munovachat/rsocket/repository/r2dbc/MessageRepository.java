package com.space.munovachat.rsocket.repository.r2dbc;

import com.space.munovachat.rsocket.entity.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {
}
