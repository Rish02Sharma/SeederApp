package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.dto.CashkickContractDto;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.entity.UserContract;
import com.zemoso.seeder.repository.UserContractRepository;
import com.zemoso.seeder.service.UserContractService;
import com.zemoso.seeder.util.NumberUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserContractServiceImpl implements UserContractService {
    private final UserContractRepository userContractRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addUserContract(CashkickContractDto contractRequest, User user, Cashkick cashkick) {
        UserContract userContract = new UserContract();
        Contract c = modelMapper.map(contractRequest, Contract.class);
        userContract.setContract(c);
        userContract.setCashkickId(cashkick.getId());
        userContract.setUser(user);
        userContract.setPaymentAvailed(NumberUtils.roundToTwoDecimalPlaces(contractRequest.getPaymentAvailed()));
        userContractRepository.save(userContract);
    }

    @Override
    public List<UserContract> findAllByCashkickId(long cashkickId) {
        return userContractRepository.findAllByCashkickId(cashkickId);
    }
}
