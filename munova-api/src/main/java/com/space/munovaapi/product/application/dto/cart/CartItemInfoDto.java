package com.space.munovaapi.product.application.dto.cart;

public record CartItemInfoDto (Long cartId,
                               Long productDetailId,
                               int quantity){
}
