package com.zemoso.seeder.controller;

import com.zemoso.seeder.dto.ApproveCashkickResponse;
import com.zemoso.seeder.dto.CashkickRequestDto;
import com.zemoso.seeder.dto.CashkickResponseDto;
import com.zemoso.seeder.service.CashkickService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cashkick")
public class CashkickController {

    private final CashkickService cashkickService;

    @PostMapping
    ResponseEntity<CashkickResponseDto> createNewCashkick(@RequestBody CashkickRequestDto cashkickRequestDto){
        CashkickResponseDto cashkick = cashkickService.createNewCashkick(cashkickRequestDto);
        return new ResponseEntity<>(cashkick, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<CashkickResponseDto>> getUsersCashkick(@PathVariable @NonNull Long userId){
        return new ResponseEntity<>(cashkickService.getUsersCashkick(userId), HttpStatus.OK);
    }

    @PostMapping("/approve/{cashkickId}")
    ResponseEntity<ApproveCashkickResponse> approveCashkick(@PathVariable Long cashkickId){
        ApproveCashkickResponse response = cashkickService.approveCashkick(cashkickId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}