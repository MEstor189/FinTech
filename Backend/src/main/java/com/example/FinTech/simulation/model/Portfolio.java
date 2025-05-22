package com.example.FinTech.simulation.model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
        private final List<Position> openPositions = new ArrayList<>();

    public void addPosition(Position position) {
        openPositions.add(position);
    }

    public List<Position> getOpenPositions() {
        return new ArrayList<>(openPositions);
    }

    public boolean isEmpty() {
        return openPositions.isEmpty();
    }

    public void clearPositions() {
        openPositions.clear();
    }

    public double getCurrentValue(double currentPrice, String symbol) {
        return openPositions.stream()
                .filter(p -> p.getSymbol().equals(symbol))
                .mapToDouble(p -> p.getQuantity() * currentPrice)
                .sum();
    }

    public int getPositionCount() {
        return openPositions.size();
    }
}
