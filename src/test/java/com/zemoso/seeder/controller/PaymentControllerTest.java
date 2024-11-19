package com.zemoso.seeder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoso.seeder.dto.UpcomingPaymentDto;
import com.zemoso.seeder.entity.Payment;
import com.zemoso.seeder.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        reset(paymentService);
    }

    @Test
    void getUsersCashkick_ShouldReturnListOfUpcomingPayments() throws Exception {
        List<UpcomingPaymentDto> upcomingPaymentDtos = new ArrayList<>();
        LocalDate dueDate = LocalDate.now().plusDays(1);
        UpcomingPaymentDto up = new UpcomingPaymentDto();
        up.setId(1L);
        up.setStatus(Payment.STATUS.UPCOMING);
        up.setDueDate(dueDate.plusMonths(1));
        up.setOutstanding(12000.0);
        up.setAmount(1000.0);
        up.setMaturityDate(up.getDueDate().plusMonths(12));
        upcomingPaymentDtos.add(up);

        UpcomingPaymentDto up2 = new UpcomingPaymentDto();
        up2.setId(2L);
        up2.setStatus(Payment.STATUS.UPCOMING);
        up2.setDueDate(dueDate.plusMonths(2));
        up2.setOutstanding(11000.0);
        up2.setAmount(1000.0);
        up2.setMaturityDate(up.getDueDate().plusMonths(12));
        upcomingPaymentDtos.add(up2);

        UpcomingPaymentDto up3 = new UpcomingPaymentDto();
        up3.setId(3L);
        up3.setStatus(Payment.STATUS.UPCOMING);
        up3.setDueDate(dueDate.plusMonths(3));
        up3.setOutstanding(10000.0);
        up3.setAmount(1000.0);
        up3.setMaturityDate(up.getDueDate().plusMonths(12));
        upcomingPaymentDtos.add(up3);

        UpcomingPaymentDto up4 = new UpcomingPaymentDto();
        up4.setId(4L);
        up4.setStatus(Payment.STATUS.UPCOMING);
        up4.setDueDate(dueDate.plusMonths(4));
        up4.setOutstanding(9000.0);
        up4.setAmount(1000.0);
        up4.setMaturityDate(up.getDueDate().plusMonths(12));
        upcomingPaymentDtos.add(up4);

        UpcomingPaymentDto up5 = new UpcomingPaymentDto();
        up5.setId(5L);
        up5.setStatus(Payment.STATUS.UPCOMING);
        up5.setDueDate(dueDate.plusMonths(5));
        up5.setOutstanding(8000.0);
        up5.setAmount(1000.0);
        up5.setMaturityDate(up.getDueDate().plusMonths(12));
        upcomingPaymentDtos.add(up5);

        UpcomingPaymentDto up6 = new UpcomingPaymentDto();
        up6.setId(6L);
        up6.setStatus(Payment.STATUS.UPCOMING);
        up6.setDueDate(dueDate.plusMonths(6));
        up6.setOutstanding(7000.0);
        up6.setAmount(1000.0);
        up6.setMaturityDate(up.getDueDate().plusMonths(12));
        upcomingPaymentDtos.add(up6);



        when(paymentService.getUpcomingPaymentsForUser(1L)).thenReturn(upcomingPaymentDtos);
        ResultActions response = mockMvc.perform(get("/payment/upcoming/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].dueDate").value(dueDate.plusMonths(1).toString()))
                .andExpect(jsonPath("$[1].dueDate").value(dueDate.plusMonths(2).toString()))
                .andExpect(jsonPath("$[2].dueDate").value(dueDate.plusMonths(3).toString()))
                .andExpect(jsonPath("$[3].dueDate").value(dueDate.plusMonths(4).toString()))
                .andExpect(jsonPath("$[4].dueDate").value(dueDate.plusMonths(5).toString()))
                .andExpect(jsonPath("$[5].dueDate").value(dueDate.plusMonths(6).toString()));

    }

    @Test
    void completePayment_ShouldReturnPAIDPaymentDetails_WhenPaymentIsCompleted() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(Payment.STATUS.PAID);
        payment.setDueDate(LocalDate.now().plusMonths(1));
        payment.setOutstanding(12000.0);
        payment.setAmount(1000.0);
        payment.setMaturityDate(payment.getDueDate().plusMonths(12));

        when(paymentService.completePayment(1L)).thenReturn(payment);

        ResultActions response = mockMvc.perform(post("/payment/complete/1"));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("PAID"));
    }
}
