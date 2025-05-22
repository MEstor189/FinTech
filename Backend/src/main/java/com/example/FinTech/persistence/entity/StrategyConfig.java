package com.example.FinTech.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.FinTech.simulation.strategy.entry.EntryStrategyType;
import com.example.FinTech.simulation.strategy.exit.ExitStrategyType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "strategy_config")
public class StrategyConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public EntryStrategyTypeEntity getEntryStrategy() {
        return entryStrategy;
    }

    public void setEntryStrategy(EntryStrategyTypeEntity entryStrategy) {
        this.entryStrategy = entryStrategy;
    }

    public ExitStrategyTypeEntity getExitStrategy() {
        return exitStrategy;
    }

    public void setExitStrategy(ExitStrategyTypeEntity exitStrategy) {
        this.exitStrategy = exitStrategy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<StrategyParamValue> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<StrategyParamValue> paramValues) {
        this.paramValues = paramValues;
    }

    @ManyToOne
    @JoinColumn(name = "entry_strategy_id")
    private EntryStrategyTypeEntity entryStrategy;

    @ManyToOne
    @JoinColumn(name = "exit_strategy_id")
    private ExitStrategyTypeEntity exitStrategy;

    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "config", cascade = CascadeType.ALL)
    private List<StrategyParamValue> paramValues;
}
