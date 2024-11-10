package com.zemoso.seeder.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CashkickRequestDto {
    private String name;
    private Long userID;
    private Double totalFinanced;
    private Double totalOutstanding;
    private List<Long> contractIds;
}
