package com.payment.service.Service;

import com.payment.service.Entity.Payment;
import com.payment.service.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    RestTemplate restTemplate;

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository, RestTemplate restTemplate) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
    }


    public Payment CreatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public void cancelPayment(Long id) {
        paymentRepository.deleteById(id);
    }

}
