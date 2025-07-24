package com.payment.service.Service;

import com.payment.service.Entity.Payment;
import com.payment.service.Enum.PaymentMethodEnum;
import com.payment.service.Enum.PaymentStatusEnum;
import com.payment.service.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
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


    public Payment initiatePayment(Payment paymentJson) {

//        String OrderServiceUrl = "http://localhost:8081/api/orders/" + paymentJson.getOrderId();
//        Payment payment1 = restTemplate.getForObject(OrderServiceUrl, Payment.class);

//        if (payment1 == null) {
//            throw new RuntimeException("order not found.");
//        }

        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatusEnum.INITIATED);
        payment.setPaymentMethod(paymentJson.getPaymentMethod());
        payment.setAmount(paymentJson.getAmount());
        payment.setTimestamp(new Date());
        payment.setOrderId(paymentJson.getOrderId());

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

    public Payment statusRetrieval(Payment payment) {
        return paymentRepository.save(payment);
    }

}
