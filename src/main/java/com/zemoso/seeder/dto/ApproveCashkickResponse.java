package com.zemoso.seeder.dto;

import com.zemoso.seeder.entity.Cashkick;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveCashkickResponse {
    private Long id;
    private String name;
    private Long userId;
    private Cashkick.STATUS status;
}
