package com.example.FinTech.dto.response.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParamKey {
    private Long id;
    private String key;
    private String type;
    private String description;
}
