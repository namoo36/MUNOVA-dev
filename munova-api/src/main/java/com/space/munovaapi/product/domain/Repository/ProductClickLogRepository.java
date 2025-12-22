package com.space.munovaapi.product.domain.Repository;

import com.space.munovaapi.product.domain.ProductClickLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductClickLogRepository extends JpaRepository<ProductClickLog, Long> {
}
