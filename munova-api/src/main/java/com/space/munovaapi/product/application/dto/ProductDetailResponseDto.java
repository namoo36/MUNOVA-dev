package com.space.munovaapi.product.application.dto;


import java.util.List;


public record ProductDetailResponseDto(ProductInfoDto productInfoDto,
                                       ProductImageDto productImageDto,
                                       List<ProductDetailInfoDto> detailInfos
                                       ) {
}
