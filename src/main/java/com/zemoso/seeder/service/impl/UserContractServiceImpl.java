package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.entity.UserContract;
import com.zemoso.seeder.repository.UserContractRepository;
import com.zemoso.seeder.service.UserContractService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserContractServiceImpl implements UserContractService {
    private final UserContractRepository userContractRepository;

    @Override
    public void addUserContract(Contract contract, User user, Cashkick cashkick) {
        UserContract userContract = new UserContract();
        userContract.setContract(contract);
        userContract.setCashkick(cashkick);
        userContract.setUser(user);
        userContractRepository.save(userContract);
    }

    @Override
    public List<UserContract> findAllByCashkickId(long cashkickId) {
        return userContractRepository.findAllByCashkickId(cashkickId);
    }
}
