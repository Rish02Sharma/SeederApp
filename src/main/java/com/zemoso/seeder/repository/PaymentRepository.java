package com.zemoso.seeder.repository;

import com.zemoso.seeder.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findTopByUserIdOrderByDueDateDesc(Long userId);
}
