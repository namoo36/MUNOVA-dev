package com.space.munovaapi.product.domain.Repository;

import com.space.munovaapi.product.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<Option, Long> {
}
