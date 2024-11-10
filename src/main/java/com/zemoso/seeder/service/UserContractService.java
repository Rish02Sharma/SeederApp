package com.zemoso.seeder.service;

import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.entity.UserContract;

import java.util.List;

public interface UserContractService {

    void addUserContract(Contract contract, User user, Cashkick cashkick);
    List<UserContract> findAllByCashkickId(long cashkickId);
}
