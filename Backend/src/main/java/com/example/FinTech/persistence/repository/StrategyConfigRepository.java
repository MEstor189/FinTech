package com.example.FinTech.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinTech.persistence.entity.StrategyConfig;

@Repository
public interface StrategyConfigRepository extends JpaRepository<StrategyConfig, Long>{
    List<StrategyConfig> findByName(String name);

}
