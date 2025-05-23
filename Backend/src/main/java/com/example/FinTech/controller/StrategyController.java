package com.example.FinTech.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinTech.dto.request.CreateStrategyRequest;
import com.example.FinTech.dto.response.strategy.StrategyConfigRequest;
import com.example.FinTech.dto.response.strategy.StrategyTypeWithParams;
import com.example.FinTech.service.StrategyConfigService;
import com.example.FinTech.service.StrategyTypeService;

@RestController
@RequestMapping("/api/strategies")
public class StrategyController {

    private final StrategyConfigService strategyService;
    private final StrategyTypeService strategyTypeService;

    @Autowired
    public StrategyController(StrategyConfigService strategyService,
            StrategyTypeService strategyTypeService) {
        this.strategyService = strategyService;
        this.strategyTypeService = strategyTypeService;
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
    public ResponseEntity<Map<String, List<StrategyTypeWithParams>>> getFullStrategyTypeInfo() {
        return ResponseEntity.ok(Map.of(
                "entryStrategies", strategyTypeService.getEntryStrategies(),
                "exitStrategies", strategyTypeService.getExitStrategies()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrategy(@PathVariable Long id) {
        strategyService.deleteStrategyById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StrategyConfigRequest> updateStrategy(
            @PathVariable Long id,
            @RequestBody CreateStrategyRequest request) {
        StrategyConfigRequest updated = strategyService.updateStrategy(id, request);
        return ResponseEntity.ok(updated);
    }
}
