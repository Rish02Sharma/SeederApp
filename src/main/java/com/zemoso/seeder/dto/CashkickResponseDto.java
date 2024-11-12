package com.zemoso.seeder.dto;

import com.zemoso.seeder.entity.Cashkick;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CashkickResponseDto {
    private Long id;
    private String name;
    private Cashkick.STATUS status;
    private LocalDate endDate;
    private double totalFinanced;
    private List<CashkickContractDto> contracts;
}
