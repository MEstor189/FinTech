package com.example.FinTech.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "strategy_config")
public class StrategyConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
