package com.zemoso.seeder.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashkickContractDto {
    private Long id;
    private String name;
    private String type;
    private Double totalAvailable;
    private Double paymentAvailed;
    private Double perPayment;
    private Double rate;
    private int termLength;
}
