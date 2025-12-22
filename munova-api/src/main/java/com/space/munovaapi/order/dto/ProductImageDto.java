package com.space.munovaapi.order.dto;

import com.space.munovaapi.product.domain.ProductImage;

public record ProductImageDto(
        Long imageId,
        String savedName,
        String imageType
) {
    public static ProductImageDto from(ProductImage productImage) {
        return new ProductImageDto(
                productImage.getId(),
                productImage.getImgUrl(),
                productImage.getImageType().name()
        );
    }
}
