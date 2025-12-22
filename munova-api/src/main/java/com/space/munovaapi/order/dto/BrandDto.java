package com.space.munovaapi.order.dto;

import com.space.munovaapi.product.domain.Brand;

public record BrandDto(
        Long brandId,
        String brandName
) {
    public static BrandDto from(Brand brand) {
        return new BrandDto(
                brand.getId(),
                brand.getBrandName()
        );
    }
}
