package com.space.munovaapi.product.application.event;

import java.util.List;

public record ProductDeleteEventForCartDto(List<Long> productDetailIds, boolean isDeleted) {
}
