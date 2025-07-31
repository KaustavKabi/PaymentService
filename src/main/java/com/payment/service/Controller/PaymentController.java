package com.payment.service.Service;

import com.payment.service.Entity.Payment;
import com.payment.service.Enum.PaymentStatusEnum;
import com.payment.service.Repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableRetry
public class PaymentService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private PaymentRepository paymentRepository;
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public PaymentService(PaymentRepository paymentRepository, RestTemplate restTemplate) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
    }

    @Retryable(
            value = { ResourceAccessException.class, HttpServerErrorException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public Payment initiatePayment(Payment paymentJson) {

//        String OrderServiceUrl = "http://localhost:8081/api/orders/" + paymentJson.getOrderId();
//        Payment payment1 = restTemplate.getForObject(OrderServiceUrl, Payment.class);

//        if (payment1 == null) {
//            throw new RuntimeException("order not found.");
//        }
        logger.info("Initiating payment for orderId {}", paymentJson.getOrderId());

        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatusEnum.INITIATED);
        payment.setPaymentMethod(paymentJson.getPaymentMethod());
        payment.setAmount(paymentJson.getAmount());
        payment.setTimestamp(new Date());
        payment.setOrderId(paymentJson.getOrderId());

        return paymentRepository.save(payment);
    }

//    public boolean processRefund(Long id) {
//        Optional<Payment> paymentOpt = paymentRepository.findById(id);
//        if (paymentOpt.isPresent()) {
//            Payment payment = paymentOpt.get();
//            if (payment.getPaymentStatus() != PaymentStatusEnum.REFUNDED) {
//                payment.setPaymentStatus(PaymentStatusEnum.REFUNDED);
//                paymentRepository.save(payment);
//                return true;
//            }
//        }
//        return false;
//    }

//    public Payment updatePayment(Payment original, Payment updatedData) {
//        original.setAmount(updatedData.getAmount());
//        original.setPaymentMethod(updatedData.getPaymentMethod());
//        original.setPaymentStatus(updatedData.getPaymentStatus());
//        return paymentRepository.save(original);
//    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }


}
