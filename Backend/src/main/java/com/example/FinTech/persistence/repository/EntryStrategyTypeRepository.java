package com.example.FinTech.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinTech.persistence.entity.EntryStrategyTypeEntity;

@Repository
public interface EntryStrategyTypeRepository extends JpaRepository<EntryStrategyTypeEntity, Long> {

    Optional<EntryStrategyTypeEntity> findByName(String name);

    EntryStrategyTypeEntity save(EntryStrategyTypeEntity entryStrategyTypeEntity);  
}
