package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.UpcomingPaymentDto;
import com.zemoso.seeder.entity.Cashkick;

import java.util.List;

public interface PaymentService {
    void createInstallmentForCashkick(Cashkick cashkick);

    List<UpcomingPaymentDto> getUpcomingPaymentsForUser(long userId);

    void completePayment(Long paymentId);
}
