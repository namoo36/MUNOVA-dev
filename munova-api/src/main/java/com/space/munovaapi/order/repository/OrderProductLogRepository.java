package com.space.munovaapi.order.repository;

import com.space.munovaapi.order.entity.OrderProductLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductLogRepository extends JpaRepository<OrderProductLog, Long> {
}
