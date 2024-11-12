package com.zemoso.seeder.controller;

import com.zemoso.seeder.dto.CashkickRequestDto;
import com.zemoso.seeder.dto.CashkickResponseDto;
import com.zemoso.seeder.entity.Cashkick;
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
    ResponseEntity<Cashkick> createNewCashkick(@RequestBody CashkickRequestDto cashkickRequestDto){
        if(cashkickRequestDto.getContractData().isEmpty()){
            throw new IllegalArgumentException("Contract data cant be empty while creating a cashkick");
        }

        Cashkick cashkick = cashkickService.createNewCashkick(cashkickRequestDto);
        return new ResponseEntity<>(cashkick, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<CashkickResponseDto>> getUsersCashkick(@PathVariable @NonNull Long userId){
        return new ResponseEntity<>(cashkickService.getUsersCashkick(userId), HttpStatus.OK);
    }

    @PostMapping("/approve/{cashkickId}")
    ResponseEntity<Cashkick> approveCashkick(@PathVariable Long cashkickId){
        Cashkick response = cashkickService.approveCashkick(cashkickId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}