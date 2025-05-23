package com.example.FinTech.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FinTech.dto.response.strategy.ParamKey;
import com.example.FinTech.dto.response.strategy.StrategyTypeWithParams;
import com.example.FinTech.persistence.entity.StrategyParamKey;
import com.example.FinTech.persistence.repository.EntryStrategyTypeRepository;
import com.example.FinTech.persistence.repository.ExitStrategyTypeRepository;
import com.example.FinTech.persistence.repository.StrategyParamKeyRepository;

@Service
public class StrategyTypeService {

    private final EntryStrategyTypeRepository entryRepo;
    private final ExitStrategyTypeRepository exitRepo;
    private final StrategyParamKeyRepository paramKeyRepo;

    private final Map<String, String> strategyDescriptions = Map.of(
        "BUY_THE_DIP", "Kauft, wenn die Aktie am Vortag um X % gefallen ist.",
        "MOMENTUM", "Kauft, wenn die letzten X Tage positiv verlaufen sind.",
        "MOVING_AVERAGE", "Kauft, wenn ein kurzer Durchschnitt einen langen schneidet.",
        "TARGET_PROFIT", "Verkauft bei Erreichen eines Zielgewinns.",
        "MOVING_AVERAGE_CROSS", "Verkauft beim Schnittpunkt zweier Durchschnitte.",
        "TRAILING_STOP", "Verkauft bei Rückfall um X % vom Hoch."
    );

    private final Map<String, String> paramDescriptions = Map.of(
        "dipThreshold", "Schwelle in Prozent, ab der gekauft wird.",
        "momentumDays", "Anzahl der Tage für Momentum-Bewertung.",
        "profitTarget", "Zielgewinn in Prozent, bei dem verkauft wird.",
        "shortPeriod", "Kurze Periode für gleitenden Durchschnitt.",
        "longPeriod", "Lange Periode für gleitenden Durchschnitt.",
        "trailingStop", "Prozentualer Abstand zum Hoch bei Verkauf."
    );

    public StrategyTypeService(
            EntryStrategyTypeRepository entryRepo,
            ExitStrategyTypeRepository exitRepo,
            StrategyParamKeyRepository paramKeyRepo) {
        this.entryRepo = entryRepo;
        this.exitRepo = exitRepo;
        this.paramKeyRepo = paramKeyRepo;
    }

    public List<StrategyTypeWithParams> getEntryStrategies() {
        return entryRepo.findAll().stream()
                .map(e -> {
                    StrategyTypeWithParams dto = new StrategyTypeWithParams();
                    dto.setId(e.getId());
                    dto.setName(e.getName());
                    dto.setDescription(strategyDescriptions.getOrDefault(e.getName(), ""));
                    dto.setParams(mapParams(paramKeyRepo.findByIsEntryAndStrategyTypeId(true,e.getId() )));
                    return dto;
                })
                .toList();
    }

    public List<StrategyTypeWithParams> getExitStrategies() {
        return exitRepo.findAll().stream()
                .map(e -> {
                    StrategyTypeWithParams dto = new StrategyTypeWithParams();
                    dto.setId(e.getId());
                    dto.setName(e.getName());
                    dto.setDescription(strategyDescriptions.getOrDefault(e.getName(), ""));
                    dto.setParams(mapParams(paramKeyRepo.findByIsEntryAndStrategyTypeId(false,e.getId())));
                    return dto;
                })
                .toList();
    }

    private List<ParamKey> mapParams(List<StrategyParamKey> keys) {
        return keys.stream()
                .map(k -> {
                    ParamKey dto = new ParamKey();
                    dto.setId(k.getId());
                    dto.setKey(k.getKey());
                    dto.setType(k.getType());
                    dto.setDescription(paramDescriptions.getOrDefault(k.getKey(), ""));
                    return dto;
                })
                .toList();
    }
}

