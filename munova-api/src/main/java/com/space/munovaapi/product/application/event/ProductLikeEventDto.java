package com.space.munovaapi.product.application.event;

public record ProductLikeEventDto(Long productId, boolean isDeleted) {
}
