package com.example.FinTech.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.FinTech.persistence.entity.EntryStrategyTypeEntity;
import com.example.FinTech.persistence.entity.ExitStrategyTypeEntity;
import com.example.FinTech.persistence.entity.StrategyParamKey;
import com.example.FinTech.persistence.repository.EntryStrategyTypeRepository;
import com.example.FinTech.persistence.repository.ExitStrategyTypeRepository;
import com.example.FinTech.persistence.repository.StrategyParamKeyRepository;

@Component
public class StrategyInitializer implements CommandLineRunner {

    private final EntryStrategyTypeRepository entryRepo;
    private final ExitStrategyTypeRepository exitRepo;
    private final StrategyParamKeyRepository paramKeyRepo;

    public StrategyInitializer(
            EntryStrategyTypeRepository entryRepo,
            ExitStrategyTypeRepository exitRepo,
            StrategyParamKeyRepository paramKeyRepo
    ) {
        this.entryRepo = entryRepo;
        this.exitRepo = exitRepo;
        this.paramKeyRepo = paramKeyRepo;
    }

@Override
public void run(String... args) {
    System.out.println("Initialisiere Strategien...");

    saveIfNotExists(entryRepo, "BUY_THE_DIP");
    saveIfNotExists(entryRepo, "MOMENTUM");
    saveIfNotExists(entryRepo, "MOVING_AVERAGE");

    saveIfNotExists(exitRepo, "TARGET_PROFIT");
    saveIfNotExists(exitRepo, "MOVING_AVERAGE_CROSS");
    saveIfNotExists(exitRepo, "TRAILING_STOP");

    saveParamIfNotExists(true, "BUY_THE_DIP", "dipThreshold", "double");
    saveParamIfNotExists(true, "MOMENTUM", "momentumDays", "int");
    saveParamIfNotExists(true, "MOVING_AVERAGE", "shortPeriod", "int");
    saveParamIfNotExists(true, "MOVING_AVERAGE", "longPeriod", "int");

    saveParamIfNotExists(false, "TARGET_PROFIT", "profitTarget", "double");
    saveParamIfNotExists(false, "MOVING_AVERAGE_CROSS", "shortPeriod", "int");
    saveParamIfNotExists(false, "MOVING_AVERAGE_CROSS", "longPeriod", "int");
    saveParamIfNotExists(false, "TRAILING_STOP", "trailingStop", "double");
}

private void saveIfNotExists(EntryStrategyTypeRepository repo, String name) {
    if (repo.findByName(name).isEmpty()) {
        EntryStrategyTypeEntity e = new EntryStrategyTypeEntity();
        e.setName(name);
        repo.save(e);
    }
}

private void saveIfNotExists(ExitStrategyTypeRepository repo, String name) {
    if (repo.findByName(name).isEmpty()) {
        ExitStrategyTypeEntity e = new ExitStrategyTypeEntity();
        e.setName(name);
        repo.save(e);
    }
}

private void saveParamIfNotExists(boolean isEntry, String strategyName, String key, String type) {
    Long strategyId = isEntry
            ? entryRepo.findByName(strategyName).map(EntryStrategyTypeEntity::getId).orElse(null)
            : exitRepo.findByName(strategyName).map(ExitStrategyTypeEntity::getId).orElse(null);

    if (strategyId == null) {
        System.err.println("Strategie-Typ nicht gefunden: " + strategyName);
        return;
    }

    boolean exists = paramKeyRepo.existsByStrategyTypeIdAndKey(strategyId, key);
    if (!exists) {
        StrategyParamKey param = new StrategyParamKey();
        param.setEntry(isEntry);
        param.setStrategyTypeId(strategyId);
        param.setKey(key);
        param.setType(type);
        paramKeyRepo.save(param);
    }
}
}