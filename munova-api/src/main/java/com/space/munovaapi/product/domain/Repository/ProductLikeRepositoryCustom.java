package com.space.munovaapi.product.domain.Repository;

import com.space.munovaapi.product.application.dto.FindProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductLikeRepositoryCustom {
    Page<FindProductResponseDto> findLikeProductByMemberId(Pageable pageable, Long memberId);
}
