package com.example.FinTech.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStrategyRequest {

    private String name;
    private Long entryStrategyTypeId;
    private Long exitStrategyTypeId;
    private List<ParamValueRequest> paramValues;

    @Getter
    @Setter
    public static class ParamValueRequest {
        private Long paramKeyId;
        private String value;
    }
}
