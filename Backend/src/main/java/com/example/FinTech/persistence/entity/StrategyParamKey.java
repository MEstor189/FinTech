package com.example.FinTech.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "strategy_param_key")
public class StrategyParamKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isEntry;

    @Column(name = "strategy_type_id")
    private Long strategyTypeId;

    @Column(name = "`key`")
    private String key;

    private String type;

    public StrategyParamKey(boolean isEntry, Long strategyTypeId, String key, String type) {
    this.isEntry = isEntry;
    this.strategyTypeId = strategyTypeId;
    this.key = key;
    this.type = type;
    }

    public StrategyParamKey(){};



}