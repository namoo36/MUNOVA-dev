package com.space.munovaapi.recommend.repository;

import com.space.munovaapi.recommend.domain.UserActionSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserActionSummaryRepository extends JpaRepository<UserActionSummary, Long> {
    Optional<UserActionSummary> findByMemberIdAndProductId(Long userId, Long productId);
    List<UserActionSummary>  findByMemberId(Long userId);
}