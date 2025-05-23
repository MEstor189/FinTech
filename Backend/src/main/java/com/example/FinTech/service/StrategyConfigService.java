
package com.example.FinTech.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.FinTech.dto.request.CreateStrategyRequest;
import com.example.FinTech.dto.response.strategy.StrategyConfigRequest;
import com.example.FinTech.exceptions.NotFoundException;
import com.example.FinTech.mapper.StrategyConfigMapper;
import com.example.FinTech.persistence.entity.EntryStrategyTypeEntity;
import com.example.FinTech.persistence.entity.ExitStrategyTypeEntity;
import com.example.FinTech.persistence.entity.StrategyConfig;
import com.example.FinTech.persistence.entity.StrategyParamKey;
import com.example.FinTech.persistence.entity.StrategyParamValue;
import com.example.FinTech.persistence.repository.EntryStrategyTypeRepository;
import com.example.FinTech.persistence.repository.ExitStrategyTypeRepository;
import com.example.FinTech.persistence.repository.StrategyConfigRepository;
import com.example.FinTech.persistence.repository.StrategyParamKeyRepository;
import com.example.FinTech.persistence.repository.StrategyParamValueRepository;

@Service
public class StrategyConfigService {

    private final StrategyConfigRepository configRepo;
    private final StrategyParamKeyRepository paramKeyRepo;
    private final StrategyParamValueRepository paramValueRepo;
    private final EntryStrategyTypeRepository entryTypeRepo;
    private final ExitStrategyTypeRepository exitTypeRepo;
    private final StrategyConfigMapper mapper;

    public StrategyConfigService(
            StrategyConfigRepository configRepo,
            StrategyParamKeyRepository paramKeyRepo,
            StrategyParamValueRepository paramValueRepo,
            EntryStrategyTypeRepository entryTypeRepo,
            ExitStrategyTypeRepository exitTypeRepo,
            StrategyConfigMapper mapper) {
        this.configRepo = configRepo;
        this.paramKeyRepo = paramKeyRepo;
        this.paramValueRepo = paramValueRepo;
        this.entryTypeRepo = entryTypeRepo;
        this.exitTypeRepo = exitTypeRepo;
        this.mapper = mapper;
    }

    @Transactional
    public StrategyConfigRequest createStrategy(CreateStrategyRequest request) {
        EntryStrategyTypeEntity entry = getEntryStrategyOrThrow(request.getEntryStrategyTypeId());
        ExitStrategyTypeEntity exit = getExitStrategyOrThrow(request.getExitStrategyTypeId());

        StrategyConfig config = new StrategyConfig();
        config.setName(request.getName());
        config.setEntryStrategy(entry);
        config.setExitStrategy(exit);
        config.setCreatedAt(LocalDateTime.now());

        StrategyConfig savedConfig = configRepo.save(config);

        List<StrategyParamValue> paramValues = request.getParamValues().stream().map(p -> {
            StrategyParamKey paramKey = getParamKeyOrThrow(p.getParamKeyId());

            StrategyParamValue value = new StrategyParamValue();
            value.setConfig(savedConfig);
            value.setParamKey(paramKey);
            value.setValue(p.getValue());
            return value;
        }).toList();

        paramValueRepo.saveAll(paramValues);
        savedConfig.setParamValues(paramValues);

        return mapper.toDTO(savedConfig);
    }

    @Transactional
    public void deleteStrategyById(Long id) {
        StrategyConfig config = configRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Strategy with ID " + id + " not found"));

        paramValueRepo.deleteAll(config.getParamValues());

        configRepo.delete(config);
    }

    @Transactional
    public StrategyConfigRequest updateStrategy(Long id, CreateStrategyRequest newData) {
        StrategyConfig existing = configRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Strategy with ID " + id + " not found"));

        
        EntryStrategyTypeEntity entry = getEntryStrategyOrThrow(newData.getEntryStrategyTypeId());
        ExitStrategyTypeEntity exit = getExitStrategyOrThrow(newData.getExitStrategyTypeId());

        existing.setName(newData.getName());
        existing.setEntryStrategy(entry);
        existing.setExitStrategy(exit);
        existing.setCreatedAt(LocalDateTime.now());

        
        paramValueRepo.deleteAll(existing.getParamValues());

        List<StrategyParamValue> newParams = newData.getParamValues().stream()
                .map(p -> {
                    StrategyParamKey key = getParamKeyOrThrow(p.getParamKeyId());
                    StrategyParamValue val = new StrategyParamValue();
                    val.setConfig(existing);
                    val.setParamKey(key);
                    val.setValue(p.getValue());
                    return val;
                })
                .toList();

        paramValueRepo.saveAll(newParams);
        existing.setParamValues(newParams);

        return mapper.toDTO(existing);
    }

    public List<StrategyConfigRequest> getAllStrategies() {
        return configRepo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    private EntryStrategyTypeEntity getEntryStrategyOrThrow(Long id) {
        return entryTypeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Entry strategy not found: " + id));
    }

    private ExitStrategyTypeEntity getExitStrategyOrThrow(Long id) {
        return exitTypeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Exit strategy not found: " + id));
    }

    private StrategyParamKey getParamKeyOrThrow(Long id) {
        return paramKeyRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Param key not found: " + id));
    }
}