package com.space.munovaapi.product.application.dto.cart;

import com.space.munovaapi.product.domain.enums.OptionCategory;

public record ProductInfoForCartDto(Long productId,
                                    Long cartId,
                                    Long detailId,
                                    String productName,
                                    Long productPrice,
                                    int productQuantity,
                                    int cartItemQuantity,
                                    String mainImgSrc,
                                    String brandName,
                                    Long optionId,
                                    OptionCategory optionType,
                                    String optionName) {
}
