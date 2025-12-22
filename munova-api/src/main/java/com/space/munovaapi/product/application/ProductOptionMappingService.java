package com.space.munovaapi.product.application;

import com.space.munovaapi.product.domain.ProductOptionMapping;
import com.space.munovaapi.product.domain.Repository.ProductOptionMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductOptionMappingService {
    private final ProductOptionMappingRepository productOptionMappingRepository;



    public void saveProductOptionMapping(ProductOptionMapping productOptionMapping) {
        productOptionMappingRepository.save(productOptionMapping);
    }


    public void deleteByProductDetailIds(List<Long> productDetailIds) {
        productOptionMappingRepository.deleteProductOptionMappingByProductDetailId(productDetailIds);
    }
}
