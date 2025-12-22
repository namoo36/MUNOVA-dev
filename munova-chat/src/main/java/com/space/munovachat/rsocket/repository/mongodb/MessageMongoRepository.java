package com.space.munovachat.rsocket.repository.mongodb;

import com.space.munovachat.rsocket.entity.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MessageMongoRepository extends ReactiveMongoRepository<Message, String> {
}
