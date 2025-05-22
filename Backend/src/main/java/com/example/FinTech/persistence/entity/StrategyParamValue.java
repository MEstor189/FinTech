package com.example.FinTech.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "strategy_param_value")
public class StrategyParamValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "config_id")
    private StrategyConfig config;

    @ManyToOne
    @JoinColumn(name = "param_key_id")
    private StrategyParamKey paramKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StrategyConfig getConfig() {
        return config;
    }

    public void setConfig(StrategyConfig config) {
        this.config = config;
    }

    public StrategyParamKey getParamKey() {
        return paramKey;
    }

    public void setParamKey(StrategyParamKey paramKey) {
        this.paramKey = paramKey;
    }
}
