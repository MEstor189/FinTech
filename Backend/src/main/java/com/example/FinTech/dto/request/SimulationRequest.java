package com.example.FinTech.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimulationRequest {

    @NotBlank(message = "Symbol darf nicht leer sein")
    @Pattern(regexp = "^[A-Z]{1,10}$", message = "Bitte gib ein gültiges Börsenkürzel in Großbuchstaben an (z.B. AAPL)")
    private String symbol;

    @NotNull(message = "endDate darf nicht leer sein")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "endDate darf nicht leer sein")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private double investmentPerTrade;
    private List<SimulationRequestStrategy> requestStrategies;

    public SimulationRequest() {}
}
