package com.example.FinTech.dto.response.strategy;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StrategyConfigRequest {
    private Long id;
    private String name;

    private String entryStrategyName;
    private String exitStrategyName;

    private LocalDateTime createdAt;

    private List<ParamValueDTO> paramValues;


    @Getter
    @Setter
    @NoArgsConstructor
@AllArgsConstructor
    public static class ParamValueDTO {
        private String key;
        private String type;
        private String value;
    }
}
