package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.dto.ApproveCashkickResponse;
import com.zemoso.seeder.dto.CashkickContractDto;
import com.zemoso.seeder.dto.CashkickRequestDto;
import com.zemoso.seeder.dto.CashkickResponseDto;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.entity.UserContract;
import com.zemoso.seeder.exception.ResourceNotFoundException;
import com.zemoso.seeder.repository.CashkickRepository;
import com.zemoso.seeder.service.*;
import com.zemoso.seeder.util.NumberUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CashkickServiceImpl implements CashkickService {

    private final ModelMapper modelMapper;
    private final CashkickRepository cashkickRepository;
    private final UserService userService;
    private final UserContractService userContractService;
    private final PaymentService paymentService;

    @Override
    public CashkickResponseDto createNewCashkick(CashkickRequestDto cashkickRequestDto){
        if(cashkickRequestDto.getContractData().isEmpty()){
            throw new IllegalArgumentException("Contract data cant be empty while creating a cashkick");
        }

        User user = userService.getById(cashkickRequestDto.getUserId());
        List<CashkickContractDto> contractList = cashkickRequestDto.getContractData();
        Cashkick cashkick = addCashkick(cashkickRequestDto, contractList, user);
        CashkickResponseDto responseDto = modelMapper.map(cashkick, CashkickResponseDto.class);
        responseDto.setContracts(contractList);
        addUserContracts(contractList, cashkick);
        return responseDto;
    }

    @Override
    public List<CashkickResponseDto> getUsersCashkick(long userId) {
        List<Cashkick> cashkicks = cashkickRepository.findAllByUserId(userId);
        return convertToCashkickResponse(cashkicks);
    }

    @Override
    public ApproveCashkickResponse approveCashkick(long cashkickId) {
        Cashkick cashkick = cashkickRepository.findById(cashkickId).orElseThrow(() -> new ResourceNotFoundException("Cashkick not found for Id: " + cashkickId));
        cashkick.setStatus(Cashkick.STATUS.APPROVED);
        Cashkick updatedCashkick = cashkickRepository.save(cashkick);
        paymentService.createInstallmentForCashkick(updatedCashkick);
        return modelMapper.map(updatedCashkick, ApproveCashkickResponse.class);
    }

    List<CashkickResponseDto> convertToCashkickResponse(List<Cashkick> cashkicks){
        List<CashkickResponseDto> response = new ArrayList<>();
        cashkicks.forEach(cashkick -> {
            CashkickResponseDto r = modelMapper.map(cashkick, CashkickResponseDto.class);
            List<UserContract> userContracts = userContractService.findAllByCashkickId(cashkick.getId());
            r.setContracts(getContractsFromUserContract(userContracts));
            response.add(r);
        });
        return response;
    }

    private Cashkick addCashkick(CashkickRequestDto cashkickRequestDto, List<CashkickContractDto> contractList, User user){
        Cashkick cashkick = modelMapper.map(cashkickRequestDto, Cashkick.class);
        double totalRecieved = NumberUtils.roundToTwoDecimalPlaces(contractList.stream()
                .mapToDouble(CashkickContractDto::getPaymentAvailed)
                .sum());

        double totalFinanced = NumberUtils.roundToTwoDecimalPlaces(totalRecieved + totalRecieved*0.12);
        cashkick.setUser(user);
        cashkick.setStartDate(LocalDate.now().plusDays(1));
        cashkick.setEndDate(LocalDate.now().plusMonths(12));
        cashkick.setStatus(Cashkick.STATUS.PENDING);
        cashkick.setTotalFinanced(totalFinanced);
        cashkick.setTotalRecieved(totalRecieved);
        cashkick.setTotalOutstanding(totalFinanced);
        user.setAvailableCredit(user.getAvailableCredit()-cashkick.getTotalFinanced());
        userService.updateUser(user);
        return cashkickRepository.save(cashkick);
    }

    private void addUserContracts(List<CashkickContractDto> contracts, Cashkick cashkick){
        contracts.forEach(contract -> userContractService.addUserContract(contract, cashkick.getUser(), cashkick));
    }

    private List<CashkickContractDto> getContractsFromUserContract(List<UserContract> userContracts){
        List<CashkickContractDto> list = new ArrayList<>();
        userContracts.forEach(userContract -> {
            CashkickContractDto c = modelMapper.map(userContract.getContract(), CashkickContractDto.class);
            c.setPaymentAvailed(userContract.getPaymentAvailed());
            list.add(c);
        });
        return list;
    }
}
