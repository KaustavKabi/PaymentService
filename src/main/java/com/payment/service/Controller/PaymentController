package com.payment.service.Controller;

import com.payment.service.Entity.Payment;
import com.payment.service.Service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

        private final PaymentService service;

        public PaymentController(PaymentService service) {
            this.service = service;
        }

        @PostMapping("/create")
        public ResponseEntity<Payment> createOrder(@RequestBody Payment payment) {
            Payment savePayment = service.CreatePayment(payment);
            return ResponseEntity.ok(savePayment);
        }

        @GetMapping
        public List<Payment> getAllPayments() {
            return service.getAllPayments();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
            return service.getPaymentById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> cancelPayment(@PathVariable Long id) {
            service.cancelPayment(id);
            return ResponseEntity.noContent().build();
        }

}
