package com.zemoso.seeder.repository;

import com.zemoso.seeder.entity.UserContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContractRepository extends JpaRepository<UserContract, Long> {
}
