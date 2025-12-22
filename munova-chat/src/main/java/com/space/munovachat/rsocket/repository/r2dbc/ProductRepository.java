package com.space.munovachat.rsocket.repository.r2dbc;

import com.space.munovachat.rsocket.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
