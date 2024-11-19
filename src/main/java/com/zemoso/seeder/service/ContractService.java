package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.ContractDto;
import com.zemoso.seeder.dto.ContractResponse;
import com.zemoso.seeder.entity.Contract;

import java.util.List;

public interface ContractService {
    List<ContractResponse> findAllContracts(long userId);
    Contract createNewContract(ContractDto contractDto);
}
