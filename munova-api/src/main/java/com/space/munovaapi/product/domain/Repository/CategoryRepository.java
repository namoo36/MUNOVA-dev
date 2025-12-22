package com.space.munovaapi.product.domain.Repository;

import com.space.munovaapi.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
