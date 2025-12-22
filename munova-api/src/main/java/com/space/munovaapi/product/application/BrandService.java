package com.space.munovaapi.product.application;

import com.space.munovaapi.product.application.exception.ProductException;
import com.space.munovaapi.product.domain.Brand;
import com.space.munovaapi.product.domain.Repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {
    private final BrandRepository brandRepository;

    // 브랜드 조회 메서드
    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(ProductException::notFoundBrandException);
    }
}
