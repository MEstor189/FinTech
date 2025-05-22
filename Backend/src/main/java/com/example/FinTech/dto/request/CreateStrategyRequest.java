package com.example.FinTech.dto.request;

import java.util.List;

public class CreateStrategyRequest {

    private String name;

    private Long entryStrategyTypeId;
    private Long exitStrategyTypeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEntryStrategyTypeId() {
        return entryStrategyTypeId;
    }

    public void setEntryStrategyTypeId(Long entryStrategyTypeId) {
        this.entryStrategyTypeId = entryStrategyTypeId;
    }

    public Long getExitStrategyTypeId() {
        return exitStrategyTypeId;
    }

    public void setExitStrategyTypeId(Long exitStrategyTypeId) {
        this.exitStrategyTypeId = exitStrategyTypeId;
    }

    public List<ParamValueRequest> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<ParamValueRequest> paramValues) {
        this.paramValues = paramValues;
    }

    private List<ParamValueRequest> paramValues;


    public static class ParamValueRequest {
        private Long paramKeyId;
        public Long getParamKeyId() {
            return paramKeyId;
        }
        public void setParamKeyId(Long paramKeyId) {
            this.paramKeyId = paramKeyId;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        private String value;


    }
}
