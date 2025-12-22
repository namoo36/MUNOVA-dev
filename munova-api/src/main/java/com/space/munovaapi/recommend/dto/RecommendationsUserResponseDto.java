package com.space.munovaapi.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationsUserResponseDto {
    private Long memberId;
    private Long productId;
    private Double score;
    private LocalDateTime createdAt;
}
