package com.space.munovaapi.product.application.dto;

import java.util.List;

public record CreateProductConditionsResponseDto (List<ProductOptionResponseDto> options,
                                                  List<ProductCategoryResponseDto> productCategories) {
}
