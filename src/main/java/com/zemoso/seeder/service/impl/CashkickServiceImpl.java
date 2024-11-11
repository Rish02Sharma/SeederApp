package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.dto.CashkickRequestDto;
import com.zemoso.seeder.dto.CashkickResponseDto;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.repository.CashkickRepository;
import com.zemoso.seeder.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CashkickServiceImpl implements CashkickService {

    private final ModelMapper modelMapper;
    private final CashkickRepository cashkickRepository;
    private final UserService userService;
    private final ContractService contractService;
    private final UserContractService userContractService;
    private final PaymentService paymentService;

    @Override
    public void createNewCashkick(CashkickRequestDto cashkickRequestDto) {
        try {
            User user = userService.getById(cashkickRequestDto.getUserId());
            List<Contract> contractList = contractService.getAllByIds(cashkickRequestDto.getContractIds());
            Cashkick cashkick = addCashkick(cashkickRequestDto, contractList, user);
            addUserContracts(contractList, cashkick);
        }catch (Exception e){
            log.info("Exception " + e.getMessage());
        }
    }

    @Override
    public List<CashkickResponseDto> getUsersCashkick(Long userId) {
        List<Cashkick> cashkicks = cashkickRepository.findAllByUserId(userId);
        return modelMapper.map(cashkicks, new TypeToken<List<CashkickResponseDto>>() {}.getType());
    }

    @Override
    public void approveCashkick(long cashkickId) {
        Optional<Cashkick> cashkick = cashkickRepository.findById(cashkickId);
        cashkick.ifPresent(value -> {
            value.setStatus(Cashkick.STATUS.APPROVED);
            cashkickRepository.save(value);
            paymentService.createInstallmentForCashkick(value);
        });

    }

    private Cashkick addCashkick(CashkickRequestDto cashkickRequestDto, List<Contract> contractList, User user){
        Cashkick cashkick = modelMapper.map(cashkickRequestDto, Cashkick.class);

        double totalRecieved = contractList.stream()
                .mapToDouble(Contract::getTotalAvailable)
                .sum();

        double totalFinanced = totalRecieved + totalRecieved*0.12;
        cashkick.setUser(user);
        cashkick.setStartDate(LocalDate.now().plusDays(1));
        cashkick.setEndDate(LocalDate.now().plusMonths(12));
        cashkick.setStatus(Cashkick.STATUS.PENDING);
        cashkick.setContracts(contractList);
        cashkick.setTotalFinanced(totalFinanced);
        cashkick.setTotalRecieved(totalRecieved);
        cashkick.setTotalOutstanding(totalFinanced);

        user.setAvailableCredit(user.getAvailableCredit()-cashkick.getTotalFinanced());
        userService.updateUser(user, user);

        return cashkickRepository.save(cashkick);
    }

    private void addUserContracts(List<Contract> contracts, Cashkick cashkick){
        contracts.forEach(contract -> {
            userContractService.addUserContract(contract, cashkick.getUser(), cashkick);
        });
    }
}
