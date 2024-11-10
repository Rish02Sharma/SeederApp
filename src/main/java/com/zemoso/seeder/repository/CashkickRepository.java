package com.zemoso.seeder.repository;

import com.zemoso.seeder.entity.Cashkick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashkickRepository extends JpaRepository<Cashkick, Long> {
    List<Cashkick> findAllByUserId(long userId);
}
