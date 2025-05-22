import { useState, useEffect, useRef } from "react";
import { fetchSimulation } from "../services/simulation";
import {
  SimulationRequest,
  SimulationResponse,
  StockDay,
  StrategyResult,
  Trade,
} from "../types/simulation";

// Ergebnisstruktur für deine Charts
interface UseSimulationDataResult {
  isLoading: boolean;
  error: string | null;
  stockData: StockDay[];
  strategies: StrategyResult[];
  tradesByStrategy: Record<string, Trade[]>;
  performanceCurves: Record<string, { date: string; performance: number }[]>;
}

export function useSimulationData(
  request: SimulationRequest | null
): [UseSimulationDataResult, () => void] {
  const [data, setData] = useState<UseSimulationDataResult>({
    isLoading: true,
    error: null,
    stockData: [],
    strategies: [],
    tradesByStrategy: {},
    performanceCurves: {},
  });

  const cacheRef = useRef<StockDay[] | null>(null);
  const lastRequestRef = useRef<string | null>(null);

  const fetchData = async () => {
    if (!request) return;

    // Cache zurücksetzen, wenn sich der Request ändert
    const requestString = JSON.stringify(request);
    if (requestString !== lastRequestRef.current) {
      cacheRef.current = null;
      lastRequestRef.current = requestString;
      // Setze isLoading auf true, aber behalte die alten Daten
      setData(prev => ({ ...prev, isLoading: true }));
    }

    try {
      const response: SimulationResponse = await fetchSimulation(request);

      // cache stockData nur einmal
      if (!cacheRef.current) {
        cacheRef.current = response.stockData;
      }

      const tradesByStrategy: Record<string, Trade[]> = {};
      const performanceCurves: Record<string, { date: string; performance: number }[]> = {};

      for (const strategy of response.strategies) {
        tradesByStrategy[strategy.strategyName] = strategy.trades;

        // Performanceverlauf kumulieren
        let cumulative = 0;
        performanceCurves[strategy.strategyName] = strategy.trades.map((trade) => {
          cumulative += trade.profitPercent;
          return { date: trade.exitDate, performance: +cumulative.toFixed(2) };
        });
      }

      setData({
        isLoading: false,
        error: null,
        stockData: cacheRef.current || [],
        strategies: response.strategies,
        tradesByStrategy,
        performanceCurves,
      });
    } catch (err: any) {
      setData(prev => ({ ...prev, isLoading: false, error: err.message }));
    }
  };

  return [data, fetchData];
}
