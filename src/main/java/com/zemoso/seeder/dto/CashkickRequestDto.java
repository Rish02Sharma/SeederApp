package com.zemoso.seeder.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CashkickRequestDto {
    private String name;
    private Long userId;
    private List<Long> contractIds;
}
