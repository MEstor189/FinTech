package com.example.FinTech.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.FinTech.dto.response.StrategyConfigRequest;
import com.example.FinTech.persistence.entity.StrategyConfig;
import com.example.FinTech.persistence.entity.StrategyParamValue;

@Component
public class StrategyConfigMapper {

    public StrategyConfigRequest toDTO(StrategyConfig config) {
        StrategyConfigRequest dto = new StrategyConfigRequest();
        dto.setId(config.getId());
        dto.setName(config.getName());
        dto.setCreatedAt(config.getCreatedAt());
        dto.setEntryStrategyName(config.getEntryStrategy().getName());
        dto.setExitStrategyName(config.getExitStrategy().getName());

        List<StrategyConfigRequest.ParamValueDTO> paramDTOs = config.getParamValues().stream()
                .map(this::mapParam)
                .toList();

        dto.setParamValues(paramDTOs);
        return dto;
    }

    private StrategyConfigRequest.ParamValueDTO mapParam(StrategyParamValue val) {
        StrategyConfigRequest.ParamValueDTO d = new StrategyConfigRequest.ParamValueDTO();
        d.setKey(val.getParamKey().getKey());
        d.setType(val.getParamKey().getType());
        d.setValue(val.getValue());
        return d;
    }
}
