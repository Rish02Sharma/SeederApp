package com.zemoso.seeder.dto;

import com.zemoso.seeder.entity.Payment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpcomingPaymentDto {
    private Long id;
    private LocalDate dueDate;
    private Payment.STATUS status;
    private double amount;
    private double outstanding;
    private LocalDate maturityDate;
    private Long cashkickId;
}
