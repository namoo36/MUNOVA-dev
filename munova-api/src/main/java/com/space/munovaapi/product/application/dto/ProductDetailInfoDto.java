package com.space.munovaapi.product.application.dto;

import java.util.List;

public record ProductDetailInfoDto (ColorOptionDto colorOptionDto,
                                    List<ProductDetailAndSizeDto> productDetailAndSizeDtoList){
}
