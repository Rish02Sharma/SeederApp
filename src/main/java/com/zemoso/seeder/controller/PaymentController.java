package com.zemoso.seeder.controller;

import com.zemoso.seeder.entity.Payment;
import com.zemoso.seeder.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/upcoming/{userId}")
    ResponseEntity<List<Payment>> getUpcomingPaymentsForUser(@PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(paymentService.getUpcomingPaymentsForUser(userId), HttpStatus.OK);
    }

}


/* can there be two cashkicks going on at the same time? */
