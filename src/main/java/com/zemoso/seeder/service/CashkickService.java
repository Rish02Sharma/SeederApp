package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.ApproveCashkickResponse;
import com.zemoso.seeder.dto.CashkickRequestDto;
import com.zemoso.seeder.dto.CashkickResponseDto;

import java.util.List;

public interface CashkickService {
    CashkickResponseDto createNewCashkick(CashkickRequestDto cashkickRequestDto);

    List<CashkickResponseDto> getUsersCashkick(long userId);

    ApproveCashkickResponse approveCashkick(long cashkickId);

}
