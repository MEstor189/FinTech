package com.example.FinTech.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinTech.persistence.entity.StrategyParamValue;

@Repository
public interface StrategyParamValueRepository extends JpaRepository <StrategyParamValue, Long> {

    List<StrategyParamValue> findByConfigId(Long configId);
    
}
