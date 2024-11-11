package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Payment;
import com.zemoso.seeder.repository.PaymentRepository;
import com.zemoso.seeder.service.PaymentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createInstallmentForCashkick(Cashkick cashkick) {
        Payment payment = new Payment();
        payment.setUser(cashkick.getUser());
        payment.setDueDate(cashkick.getStartDate().plusMonths(1));
        payment.setStatus(Payment.STATUS.UPCOMING);
        payment.setAmount(cashkick.getTotalFinanced()/12);
        payment.setOutstanding(cashkick.getTotalOutstanding()-payment.getAmount());
        payment.setMaturityDate(cashkick.getEndDate());
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getUpcomingPaymentsForUser(long userId) {
        List<Payment> payments = paymentRepository.findByUserIdAndStatusOrderByDueDateDesc(userId, Payment.STATUS.UPCOMING.toString());
        return generateUpcomingPayments(payments);
    }

    @Override
    public void completePayment(Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if(payment.isPresent()){
            payment.get().setStatus(Payment.STATUS.PAID);
            paymentRepository.save(payment.get());
            createNextInstallmentForPayment(payment.get());
        }
    }

    private List<Payment> generateUpcomingPayments(List<Payment> payments) {
        List<Payment> response = new ArrayList<>();

        payments.forEach(p -> {
            LocalDate maturityDate = p.getMaturityDate();
            double outstanding = p.getOutstanding();
            for (int i = 0; i < 5; i++) {
                LocalDate date = p.getDueDate().plusMonths(i);

                if (!date.isAfter(maturityDate) && outstanding>0d) {
                    Payment newPayment = modelMapper.map(p, Payment.class);
                    newPayment.setDueDate(date);
                    outstanding = outstanding-newPayment.getAmount();
                    newPayment.setOutstanding(outstanding);
                    response.add(newPayment);
                }
            }
        });
        return response;
    }

    private void createNextInstallmentForPayment(Payment payment){
        Payment nextPayment = modelMapper.map(payment, Payment.class);
        if (!payment.getDueDate().isAfter(payment.getMaturityDate())) {
            nextPayment.setDueDate(payment.getDueDate().plusMonths(1));
            nextPayment.setOutstanding(payment.getOutstanding()- payment.getAmount());
            nextPayment.setStatus(Payment.STATUS.UPCOMING);
            paymentRepository.save(nextPayment);
        }
    }
}
