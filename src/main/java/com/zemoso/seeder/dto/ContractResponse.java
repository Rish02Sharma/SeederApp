package com.zemoso.seeder.dto;

import com.zemoso.seeder.entity.Contract;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractResponse {
    private Long id;
    private String name;
    private Contract.STATUS status;
    private String type;
    private Double totalAvailable;
    private Double rate;
    private int termLength;
    private Double perPayment;
}
