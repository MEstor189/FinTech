package com.example.FinTech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinTech.dto.request.CreateStrategyRequest;
import com.example.FinTech.dto.response.strategy.ParamKey;
import com.example.FinTech.dto.response.strategy.StrategyConfigRequest;
import com.example.FinTech.dto.response.strategy.StrategyTypeWithParams;
import com.example.FinTech.dto.response.strategy.StrategyTypes;
import com.example.FinTech.persistence.repository.EntryStrategyTypeRepository;
import com.example.FinTech.persistence.repository.ExitStrategyTypeRepository;
import com.example.FinTech.persistence.repository.StrategyParamKeyRepository;
import com.example.FinTech.service.StrategyConfigService;

@RestController
@RequestMapping("/api/strategies")
public class StrategyController {

     @Autowired
    private EntryStrategyTypeRepository entryRepo;
    @Autowired
    private ExitStrategyTypeRepository exitRepo;
    @Autowired
    private StrategyParamKeyRepository paramKeyRepo;
    private final StrategyConfigService strategyService;


    public StrategyController(StrategyConfigService strategyService) {
        this.strategyService = strategyService;
    }

    @PostMapping("/create")
    public ResponseEntity<StrategyConfigRequest> createStrategy(@RequestBody CreateStrategyRequest request) {
        StrategyConfigRequest created = strategyService.createStrategy(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<StrategyConfigRequest>> getAllStrategies() {
        return ResponseEntity.ok(strategyService.getAllStrategies());
    }



    @GetMapping("/getTypes")
    public StrategyTypes getFullStrategyTypeInfo() {
        var entryStrategies = entryRepo.findAll().stream()
                .map(e -> new StrategyTypeWithParams(
                        e.getId(),
                        e.getName(),
                        getParams(true, e.getId())))
                .toList();

        var exitStrategies = exitRepo.findAll().stream()
                .map(e -> new StrategyTypeWithParams(
                        e.getId(),
                        e.getName(),
                        getParams(false, e.getId())))
                .toList();

        StrategyTypes response = new StrategyTypes();
        response.setEntryStrategies(entryStrategies);
        response.setExitStrategies(exitStrategies);
        return response;
    }

    private List<ParamKey> getParams(boolean isEntry, Long strategyTypeId) {
        return paramKeyRepo.findByIsEntryAndStrategyTypeId(isEntry, strategyTypeId).stream()
                .map(p -> new ParamKey(p.getId(), p.getKey(), p.getType()))
                .toList();
    }
}
