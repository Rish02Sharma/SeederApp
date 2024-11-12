package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.CashkickContractDto;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.entity.UserContract;

import java.util.List;

public interface UserContractService {

    void addUserContract(CashkickContractDto contract, User user, Cashkick cashkick);
    List<UserContract> findAllByCashkickId(long cashkickId);
}
