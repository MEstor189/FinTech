package com.example.FinTech.dto.response.strategy;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StrategyTypeWithParams {
    private Long id;
    private String name;
    private List<ParamKey> params;
}
