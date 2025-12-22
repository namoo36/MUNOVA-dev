package com.space.munovaapi.product.application.dto;

public record ProductCategoryResponseDto (Long id,
                                          String categoryName,
                                          Long parentId,
                                          int level){
}
