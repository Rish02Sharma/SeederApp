package com.zemoso.seeder.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.zemoso.seeder.dto.UpcomingPaymentDto;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Payment;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.exception.ResourceNotFoundException;
import com.zemoso.seeder.repository.PaymentRepository;
import com.zemoso.seeder.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Cashkick cashkick;
    private Payment payment;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "Rishabh Sharma", "rishabh@example.com", "password123", 1000d);

        cashkick = new Cashkick(
                1L,
                user,
                "Cashkick 1",
                Cashkick.STATUS.PENDING,
                1200d,
                500d,
                700d,
                LocalDate.now().plusDays(1).plusMonths(12),
                LocalDate.now().plusDays(1)
        );

        payment = new Payment();
        payment.setId(1L);
        payment.setUser(user);
        payment.setAmount(100d);
        payment.setOutstanding(600d);
        payment.setDueDate(LocalDate.now().plusMonths(1));
        payment.setMaturityDate(cashkick.getEndDate());
        payment.setStatus(Payment.STATUS.UPCOMING);
        payment.setCashkickId(cashkick.getId());
    }

    @Test
    void testCreateInstallmentForCashkick() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        paymentService.createInstallmentForCashkick(cashkick);

        assertEquals(user, payment.getUser());
        assertEquals(Payment.STATUS.UPCOMING, payment.getStatus());
        assertEquals(cashkick.getTotalFinanced() / 12, payment.getAmount());
        assertEquals(cashkick.getTotalOutstanding() - payment.getAmount(), payment.getOutstanding());
        assertEquals(cashkick.getEndDate(), payment.getMaturityDate());
        assertEquals(cashkick.getId(), payment.getCashkickId());
    }

    @Test
    void testGetUpcomingPaymentsForUser_Success() {
        when(paymentRepository.findByUserIdAndStatusOrderByDueDateDesc(user.getId(), Payment.STATUS.UPCOMING))
                .thenReturn(Collections.singletonList(payment));

        UpcomingPaymentDto upcomingPaymentDto = new UpcomingPaymentDto();
        when(modelMapper.map(payment, UpcomingPaymentDto.class)).thenReturn(upcomingPaymentDto);

        List<UpcomingPaymentDto> result = paymentService.getUpcomingPaymentsForUser(user.getId());

        assertNotNull(result);
        assertEquals(6, result.size());
        assertSame(upcomingPaymentDto, result.getFirst());

        verify(paymentRepository, times(1)).findByUserIdAndStatusOrderByDueDateDesc(user.getId(), Payment.STATUS.UPCOMING);
    }

    @Test
    void testCompletePayment_Success() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment completedPayment = paymentService.completePayment(1L);

        assertEquals(Payment.STATUS.PAID, completedPayment.getStatus());

        verify(paymentRepository, times(1)).findById(1L);
        verify(paymentRepository, times(2)).save(any(Payment.class));
    }

    @Test
    void testCompletePayment_NotFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            paymentService.completePayment(1L);
        });

        assertEquals("Payment not found for paymentId 1", exception.getMessage());

        verify(paymentRepository, times(1)).findById(1L);
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testCreateNextInstallmentForPayment() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        paymentService.completePayment(1L);
        verify(paymentRepository, times(2)).save(any(Payment.class));
    }
}
