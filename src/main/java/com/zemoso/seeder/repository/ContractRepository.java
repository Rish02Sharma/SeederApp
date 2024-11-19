package com.zemoso.seeder.repository;

import com.zemoso.seeder.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findAllByTotalAvailableLessThan(double totalCredit);
}
