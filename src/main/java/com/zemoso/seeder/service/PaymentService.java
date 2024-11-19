package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.UpcomingPaymentDto;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Payment;

import java.util.List;

public interface PaymentService {
    void createInstallmentForCashkick(Cashkick cashkick);

    List<UpcomingPaymentDto> getUpcomingPaymentsForUser(long userId);

    Payment completePayment(Long paymentId);
}
