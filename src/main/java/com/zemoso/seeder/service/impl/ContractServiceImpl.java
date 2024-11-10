package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.dto.ContractDto;
import com.zemoso.seeder.dto.ContractResponse;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.repository.ContractRepository;
import com.zemoso.seeder.service.ContractService;
import com.zemoso.seeder.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ContractServiceImpl implements ContractService {

    private final UserService userService;
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ContractResponse> findAllContracts(long userId) {
        try {
            User user = userService.getById(userId);
            List<Contract> contracts = contractRepository.findAllByTotalAvailableLessThan(user.getAvailableCredit());
            return contracts.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        }catch (Exception e){
            log.info("Exception");
        }

        return List.of();
    }

    @Override
    public Contract createNewContract(ContractDto contractDto) {
        Contract contract = modelMapper.map(contractDto, Contract.class);
        return contractRepository.save(contract);
    }

    @Override
    public List<Contract> getAllByIds(List<Long> contractIds) {
        return contractIds.stream()
                .map(contractRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private ContractResponse convertToResponse(Contract contract) {
        ContractResponse contractResponse = modelMapper.map(contract, ContractResponse.class);
        double perPayment = (contract.getTotalAvailable() + (contract.getTotalAvailable()* contractResponse.getRate()/100))/contract.getTermLength();
        contractResponse.setPerPayment(perPayment);
        return contractResponse;
    }
}
