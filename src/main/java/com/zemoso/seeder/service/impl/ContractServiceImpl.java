package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.dto.ContractDto;
import com.zemoso.seeder.dto.ContractResponse;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.repository.ContractRepository;
import com.zemoso.seeder.service.ContractService;
import com.zemoso.seeder.service.UserService;
import com.zemoso.seeder.util.NumberUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final UserService userService;
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ContractResponse> findAllContracts(long userId) {
        User user = userService.getById(userId);
        List<Contract> contracts = contractRepository.findAllByTotalAvailableLessThan(user.getAvailableCredit());
        return contracts.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public Contract createNewContract(ContractDto contractDto) {
        Contract contract = modelMapper.map(contractDto, Contract.class);
        double perPayment = (contract.getTotalAvailable() + (contract.getTotalAvailable()* contract.getRate()/100))/contract.getTermLength();
        contract.setPerPayment(NumberUtils.roundToTwoDecimalPlaces(perPayment));
        return contractRepository.save(contract);
    }

    private ContractResponse convertToResponse(Contract contract) {
        return modelMapper.map(contract, ContractResponse.class);
    }
}
