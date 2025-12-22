package com.space.munovaapi.recommend.controller;

import com.space.munovaapi.core.config.ResponseApi;
import com.space.munovaapi.core.dto.PagingResponse;
import com.space.munovaapi.recommend.dto.RecommendationsProductResponseDto;
import com.space.munovaapi.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/admin/recommend/products")
@RequiredArgsConstructor
public class RecommendationProductController {

    private final RecommendService recommendService;

    //전체 상품 기반 추천 로그
    @GetMapping()
    public ResponseEntity<ResponseApi<PagingResponse<RecommendationsProductResponseDto>>> getAllProductRecommendations(@PageableDefault(size = 10, sort="CreatedAt") Pageable pageable) {
        PagingResponse<RecommendationsProductResponseDto> recommendations = recommendService.getRecommendationsByProductId(null, pageable);

        return ResponseEntity.ok().body(ResponseApi.ok(recommendations));
    }
    //{productId}의 상품 기반 추천 로그
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseApi<PagingResponse<RecommendationsProductResponseDto>>> getProductRecommendations(@PathVariable Long productId,@PageableDefault(size = 10, sort="CreatedAt") Pageable pageable) {
        PagingResponse<RecommendationsProductResponseDto> recommendations = recommendService.getRecommendationsByProductId(productId, pageable);
        return ResponseEntity.ok().body(ResponseApi.ok(recommendations));
    }
}