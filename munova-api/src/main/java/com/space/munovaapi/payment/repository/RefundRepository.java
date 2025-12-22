package com.space.munovaapi.payment.repository;

import com.space.munovaapi.payment.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    Optional<Refund> findByTransactionKey(String transactionKey);
    boolean existsByPaymentKey(String paymentKey);
}
