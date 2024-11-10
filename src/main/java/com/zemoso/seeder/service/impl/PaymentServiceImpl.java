package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Payment;
import com.zemoso.seeder.repository.PaymentRepository;
import com.zemoso.seeder.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public void createInstallmentForCashkick(Cashkick cashkick) {
        Payment payment = new Payment();
        payment.setUser(cashkick.getUser());
        payment.setDueDate(cashkick.getStartDate().plusMonths(1));
        payment.setStatus(Payment.STATUS.UPCOMING);
        payment.setAmount(cashkick.getTotalFinanced()/12);
        payment.setOutstanding(cashkick.getTotalOutstanding()-payment.getAmount());
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getUpcomingPaymentsForUser(long userId) {
        Optional<Payment> payment = paymentRepository.findTopByUserIdOrderByDueDateDesc(userId);


        return List.of();
    }
}
