package com.space.munovaapi.product.application.dto;

import com.space.munovaapi.product.domain.enums.OptionCategory;

public record ProductOptionInfoDto (Long optionId,
                                    Long detailId,
                                    OptionCategory optionType,
                                    String optionName,
                                    int quantity){
}
