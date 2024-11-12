package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.CashkickRequestDto;
import com.zemoso.seeder.dto.CashkickResponseDto;
import com.zemoso.seeder.entity.Cashkick;

import java.util.List;

public interface CashkickService {
    Cashkick createNewCashkick(CashkickRequestDto cashkickRequestDto);

    List<CashkickResponseDto> getUsersCashkick(long userId);

    Cashkick approveCashkick(long cashkickId);
}
