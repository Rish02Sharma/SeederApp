package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.CashkickRequestDto;
import com.zemoso.seeder.dto.CashkickResponseDto;

import java.util.List;

public interface CashkickService {
    void createNewCashkick(CashkickRequestDto cashkickRequestDto);

    List<CashkickResponseDto> getUsersCashkick(Long userId);

    void approveCashkick(long cashkickId);
}
