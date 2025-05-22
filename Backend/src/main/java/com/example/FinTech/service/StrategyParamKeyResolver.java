package com.example.FinTech.service;

import org.springframework.stereotype.Service;

import com.example.FinTech.persistence.entity.EntryStrategyTypeEntity;
import com.example.FinTech.persistence.entity.ExitStrategyTypeEntity;
import com.example.FinTech.persistence.entity.StrategyParamKey;
import com.example.FinTech.persistence.repository.EntryStrategyTypeRepository;
import com.example.FinTech.persistence.repository.ExitStrategyTypeRepository;

@Service
public class StrategyParamKeyResolver {

    private final EntryStrategyTypeRepository entryRepo;
    private final ExitStrategyTypeRepository exitRepo;

    public StrategyParamKeyResolver(EntryStrategyTypeRepository entryRepo,
                                    ExitStrategyTypeRepository exitRepo) {
        this.entryRepo = entryRepo;
        this.exitRepo = exitRepo;
    }

    public String getStrategyTypeName(StrategyParamKey paramKey) {
        if (paramKey.isEntry()) {
            return entryRepo.findById(paramKey.getStrategyTypeId())
                            .map(EntryStrategyTypeEntity::getName)
                            .orElse("Unknown EntryType");
        } else {
            return exitRepo.findById(paramKey.getStrategyTypeId())
                           .map(ExitStrategyTypeEntity::getName)
                           .orElse("Unknown ExitType");
        }
    }
}
