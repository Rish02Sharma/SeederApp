package com.zemoso.seeder.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String name;

    private String email;

    private String password;

    private double availableCredit;
}
