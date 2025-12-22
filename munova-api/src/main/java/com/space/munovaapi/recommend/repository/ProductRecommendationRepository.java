package com.space.munovaapi.recommend.repository;

import com.space.munovaapi.product.domain.Product;
import com.space.munovaapi.recommend.domain.ProductRecommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRecommendationRepository extends JpaRepository<ProductRecommendation, Long> {
    Page<ProductRecommendation> findBySourceProductId(Long sourceProductId, Pageable pageable);
    void deleteBySourceProduct(Product product);
    Page<ProductRecommendation> findAll(Pageable pageable);
}