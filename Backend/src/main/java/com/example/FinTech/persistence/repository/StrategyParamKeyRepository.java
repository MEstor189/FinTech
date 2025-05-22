package com.example.FinTech.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinTech.persistence.entity.StrategyParamKey;

@Repository
public interface StrategyParamKeyRepository extends JpaRepository<StrategyParamKey, Long>{

    List<StrategyParamKey> findByIsEntryAndStrategyTypeId (boolean isEntry, Long strategyTypeId);

    boolean existsByStrategyTypeIdAndKey(Long strategyTypeId, String key);
    
}
