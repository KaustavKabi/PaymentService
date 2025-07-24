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

        @PostMapping("/initiate")
        public ResponseEntity<Payment> initiatePayment(@RequestBody Payment payment) {
            Payment savePayment = service.initiatePayment(payment);
            return ResponseEntity.ok(savePayment);
        }

        @PostMapping("/status")
        public ResponseEntity<Payment> statusRetrieval(@RequestBody Payment payment) {
            Payment savePayment = service.statusRetrieval(payment);
            return ResponseEntity.ok(savePayment);
        }


        @GetMapping("/all")
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
//initiate payment,status retrieval,update,refund processing apis
