package com.zemoso.seeder.controller;

import com.zemoso.seeder.dto.PaymentCompleteDto;
import com.zemoso.seeder.dto.UserDto;
import com.zemoso.seeder.entity.Payment;
import com.zemoso.seeder.entity.User;
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
    ResponseEntity<List<Payment>> getUpcomingPaymentsForUser(@PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(paymentService.getUpcomingPaymentsForUser(userId), HttpStatus.OK);
    }

    @PostMapping("/complete/{paymentId}")
    public ResponseEntity<String> completePayment(@PathVariable Long paymentId){
        paymentService.completePayment(paymentId);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

}


/* can there be two cashkicks going on at the same time? */
