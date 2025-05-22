package com.example.FinTech.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class StrategyConfigRequest {
        private Long id;
    private String name;

    private String entryStrategyName;
    private String exitStrategyName;

    private LocalDateTime createdAt;

    private List<ParamValueDTO> paramValues;



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getEntryStrategyName() {
        return entryStrategyName;
    }



    public void setEntryStrategyName(String entryStrategyName) {
        this.entryStrategyName = entryStrategyName;
    }



    public String getExitStrategyName() {
        return exitStrategyName;
    }



    public void setExitStrategyName(String exitStrategyName) {
        this.exitStrategyName = exitStrategyName;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    public List<ParamValueDTO> getParamValues() {
        return paramValues;
    }



    public void setParamValues(List<ParamValueDTO> paramValues) {
        this.paramValues = paramValues;
    }



    public static class ParamValueDTO {
        private String key;
        private String type;
        private String value;
        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }


    }
}
