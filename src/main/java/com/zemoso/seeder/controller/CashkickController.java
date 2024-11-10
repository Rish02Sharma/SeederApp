package com.zemoso.seeder.controller;

import com.zemoso.seeder.dto.CashkickRequestDto;
import com.zemoso.seeder.dto.CashkickResponseDto;
import com.zemoso.seeder.service.CashkickService;
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
    ResponseEntity<String> createNewCashkick(@RequestBody CashkickRequestDto cashkickRequestDto){
        cashkickService.createNewCashkick(cashkickRequestDto);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<CashkickResponseDto>> getUsersCashkick(@PathVariable Long userId){
        return new ResponseEntity<>(cashkickService.getUsersCashkick(userId), HttpStatus.OK);
    }

    @PostMapping("/{cashkickId}")
    ResponseEntity<String> approveCashkick(@PathVariable Long cashkickId){
        cashkickService.approveCashkick(cashkickId);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

}