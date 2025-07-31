package com.payment.service.Repository;

import com.payment.service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
        List<Payment> findByOrderId(Long orderId);

}

