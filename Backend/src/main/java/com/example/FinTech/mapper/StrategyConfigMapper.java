package com.example.FinTech.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.FinTech.dto.response.strategy.StrategyConfigRequest;
import com.example.FinTech.dto.response.strategy.StrategyConfigRequest.ParamValueDTO;
import com.example.FinTech.persistence.entity.StrategyConfig;
import com.example.FinTech.persistence.entity.StrategyParamValue;

@Component
public class StrategyConfigMapper {

public StrategyConfigRequest toDTO(StrategyConfig config) {
    StrategyConfigRequest dto = new StrategyConfigRequest();
    dto.setId(config.getId());
    dto.setStrategyName(config.getName());
    dto.setCreatedAt(config.getCreatedAt());
    dto.setEntryStrategyName(config.getEntryStrategy().getName());
    dto.setExitStrategyName(config.getExitStrategy().getName());

    // Neue Trennung nach Entry / Exit
    List<ParamValueDTO> entryParams = new ArrayList<>();
    List<ParamValueDTO> exitParams = new ArrayList<>();

    for (StrategyParamValue val : config.getParamValues()) {
        ParamValueDTO paramDto = mapParam(val);
        if (val.getParamKey().isEntry()) {
            entryParams.add(paramDto);
        } else {
            exitParams.add(paramDto);
        }
    }

    dto.setEntryParams(entryParams);
    dto.setExitParams(exitParams);

    return dto;
}

    private StrategyConfigRequest.ParamValueDTO mapParam(StrategyParamValue val) {
        StrategyConfigRequest.ParamValueDTO d = new StrategyConfigRequest.ParamValueDTO();
        d.setKey(val.getParamKey().getKey());
        d.setType(val.getParamKey().getType());
        d.setValue(val.getValue());
        d.setInputType(mapToInputType(val.getParamKey().getType()));
        return d;
    }

    private String mapToInputType(String backendType) {
    return switch (backendType.toLowerCase()) {
        case "int", "integer", "float", "double" -> "number";
        case "boolean" -> "checkbox";
        case "date" -> "date";
        case "localdatetime" -> "datetime-local";
        default -> "text";
    };
}
}
