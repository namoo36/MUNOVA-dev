package com.space.munovaapi.coupon.service;

import com.space.munovaapi.core.dto.PagingResponse;
import com.space.munovaapi.coupon.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CouponDetailService {

    /**
     * 선착순 쿠폰조회
     */
    PagingResponse<SearchEventCouponResponse> searchEventCoupon(Pageable pageable, Sort sort, Long memberId);

    /**
     * 관리자 쿠폰조회
     */
    PagingResponse<SearchCouponDetailResponse> searchAdminCoupon(
            Pageable pageable, Sort sort, SearchCouponDetailParams searchCouponDetailParams
    );

    /**
     * 관리자 쿠폰등록
     */
    RegisterCouponDetailResponse registerCoupon(Long memberId, RegisterCouponDetailRequest registerCouponDetailRequest);
}
