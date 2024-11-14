package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.dto.UpcomingPaymentDto;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Payment;
import com.zemoso.seeder.exception.ResourceNotFoundException;
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
        payment.setCashkickId(cashkick.getId());
        paymentRepository.save(payment);
    }

    @Override
    public List<UpcomingPaymentDto> getUpcomingPaymentsForUser(long userId) {
        List<Payment> payments = paymentRepository.findByUserIdAndStatusOrderByDueDateDesc(userId, Payment.STATUS.UPCOMING);
        return generateUpcomingPayments(payments);
    }

    @Override
    public Payment completePayment(Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if(payment.isPresent()){
            payment.get().setStatus(Payment.STATUS.PAID);
            paymentRepository.save(payment.get());
            createNextInstallmentForPayment(payment.get());
            return payment.get();
        }else{
            throw new ResourceNotFoundException("Payment not found for paymentId " + paymentId);
        }
    }

    private List<UpcomingPaymentDto> generateUpcomingPayments(List<Payment> payments) {
        List<UpcomingPaymentDto> response = new ArrayList<>();

        payments.forEach(p -> {
            UpcomingPaymentDto firstPayment = modelMapper.map(p, UpcomingPaymentDto.class);
            response.add(firstPayment);

            LocalDate maturityDate = p.getMaturityDate();
            double outstanding = p.getOutstanding();
            LocalDate dueDate = p.getDueDate();
            for (int i = 0; i < 5; i++) {
                dueDate = dueDate.plusMonths(1);

                if (!dueDate.isAfter(maturityDate) && outstanding>0d) {
                    UpcomingPaymentDto newPayment = modelMapper.map(p, UpcomingPaymentDto.class);
                    newPayment.setId(null);
                    newPayment.setDueDate(dueDate);
                    outstanding = outstanding-newPayment.getAmount();
                    newPayment.setOutstanding(outstanding);
                    response.add(newPayment);
                }
            }
        });
        return response;
    }

    private void createNextInstallmentForPayment(Payment payment){
        Payment nextPayment = new Payment();
        if (!payment.getDueDate().isAfter(payment.getMaturityDate())) {
            nextPayment.setAmount(payment.getAmount());
            nextPayment.setUser(payment.getUser());
            nextPayment.setMaturityDate(payment.getMaturityDate());
            nextPayment.setDueDate(payment.getDueDate().plusMonths(1));
            nextPayment.setOutstanding(payment.getOutstanding()- payment.getAmount());
            nextPayment.setStatus(Payment.STATUS.UPCOMING);
            paymentRepository.save(nextPayment);
        }
    }
}
