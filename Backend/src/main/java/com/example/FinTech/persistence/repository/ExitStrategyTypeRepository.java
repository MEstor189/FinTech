package com.example.FinTech.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinTech.persistence.entity.ExitStrategyTypeEntity;

@Repository
public interface ExitStrategyTypeRepository extends JpaRepository<ExitStrategyTypeEntity, Long> {

    Optional<ExitStrategyTypeEntity> findByName(String name);  
}

