package com.zemoso.seeder.repository;

import com.zemoso.seeder.entity.UserContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserContractRepository extends JpaRepository<UserContract, Long> {
    List<UserContract> findAllByCashkickId(long cashkickId);
}
