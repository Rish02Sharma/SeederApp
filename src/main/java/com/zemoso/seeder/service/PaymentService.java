package com.zemoso.seeder.service;

import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Payment;

import java.util.List;

public interface PaymentService {
    void createInstallmentForCashkick(Cashkick cashkick);

    List<Payment> getUpcomingPaymentsForUser(long userId);

    void completePayment(Long paymentId);
}
