package com.space.munovaapi.product.application.event;

import java.util.List;

public record ProductDeleteEvenForLikeDto(List<Long> productId, boolean isDeleted) {
}
