package com.zemoso.seeder.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CashkickRequestDto {
    @NonNull
    private String name;
    private Long userId;
    private List<CashkickContractDto> contractData;
}
