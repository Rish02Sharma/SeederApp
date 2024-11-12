package com.zemoso.seeder.controller;

import com.zemoso.seeder.dto.ContractDto;
import com.zemoso.seeder.dto.ContractResponse;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contract")
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/available/{userId}")
    ResponseEntity<List<ContractResponse>> getAllAvailableContracts(@PathVariable Long userId){
        return new ResponseEntity<>(contractService.findAllContracts(userId), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Contract> createContract(@RequestBody ContractDto contractRequest){
        Contract contract = contractService.createNewContract(contractRequest);
        return new ResponseEntity<>(contract, HttpStatus.CREATED);
    }
}
