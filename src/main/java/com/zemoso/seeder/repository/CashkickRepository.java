package com.zemoso.seeder.repository;

import com.zemoso.seeder.entity.Cashkick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashkickRepository extends JpaRepository<Cashkick, Long> {
}
