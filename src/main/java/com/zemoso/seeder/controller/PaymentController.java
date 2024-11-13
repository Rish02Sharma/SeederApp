package com.zemoso.seeder.controller;

import com.zemoso.seeder.dto.UpcomingPaymentDto;
import com.zemoso.seeder.entity.Payment;
import com.zemoso.seeder.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/upcoming/{userId}")
    ResponseEntity<List<UpcomingPaymentDto>> getUpcomingPaymentsForUser(@PathVariable Long userId){
        return new ResponseEntity<>(paymentService.getUpcomingPaymentsForUser(userId), HttpStatus.OK);
    }

    @PostMapping("/complete/{paymentId}")
    public ResponseEntity<Payment> completePayment(@PathVariable Long paymentId){
        Payment response = paymentService.completePayment(paymentId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
