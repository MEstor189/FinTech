import React, { useState } from 'react';
import { Typography } from '@mui/material';
import SimulationControlBar from '../components/controllbar/SimulationControlBar';
import ChartSwitcher, { ChartType } from '../components/charts/ChartSwitcher';
import PriceChart from '../components/charts/PriceChart';
import StrategyCompareChart from '../components/charts/StrategyCompareChart';
import PerformanceChart from '../components/charts/PerformanceChart';
import { useSimulationData } from '../hooks/useSimulationData';
import { SimulationRequest, Trade, SimulationResponse } from '../types/simulation';
import { fetchSimulation } from '../services/simulation';
import './SimulationPage.css';
import { useAvailableStrategies } from '../hooks/useAvailableStrategies';




export default function SimulationPage() {
  const [strategyA, setStrategyA] = useState('');
  const [strategyB, setStrategyB] = useState('');
  const [timeframe, setTimeframe] = useState('01.01.2024-01.04.2024');
  const [symbol, setSymbol] = useState('AAPL');
  const [simulationStarted, setSimulationStarted] = useState(false);
  const [selectedChart, setSelectedChart] = useState<ChartType>('price');
  const {
    strategies: availableStrategies,
    isLoading: loadingStrategies,
    error: strategiesError,
    refetch: refetchStrategies,
  } = useAvailableStrategies();

  type MarkerPoint = {
    date: string;
    price: number;
    type: "BUY" | "SELL";
    trade: Trade;
  };

  // Request nur bei gestarteter Simulation generieren
  const buildRequest = (): SimulationRequest | null => {
    const stratA = availableStrategies.find((s) => s.strategyName === strategyA);
    const stratB = availableStrategies.find((s) => s.strategyName === strategyB);
    if (!stratA || !stratB) return null;

    console.log(stratA);

    const [start, end] = timeframe.split("-").map((d) => {
      const [day, month, year] = d.trim().split(".");
      return `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;
    });

    const convertParamArrayToObject = (
      paramArray: { key: string; value: string }[]
    ): Record<string, number> => {
      const result: Record<string, number> = {};
      for (const param of paramArray) {
        const num = parseFloat(param.value);
        if (!isNaN(num)) {
          result[param.key] = num;
        }
      }
      return result;
    };

    return {
      symbol,
      startDate: start,
      endDate: end,
      investmentPerTrade: 100,
      requestStrategies: [
        {
          strategyName: stratA.strategyName,
          entryStrategyType: stratA.entryStrategyName,
          exitStrategyType: stratA.exitStrategyName,
          entryParams: convertParamArrayToObject(stratA.entryParams),
          exitParams: convertParamArrayToObject(stratA.exitParams),
        },
        {
          strategyName: stratB.strategyName,
          entryStrategyType: stratB.entryStrategyName,
          exitStrategyType: stratB.exitStrategyName,
          entryParams: convertParamArrayToObject(stratB.entryParams),
          exitParams: convertParamArrayToObject(stratB.exitParams),
        },
      ],
    };
  };

  const request = simulationStarted ? buildRequest() : null;
  const [data, refetch] = useSimulationData(request);

  const handleStart = () => {
    setSimulationStarted(true);
    refetch();

    console.log("Simulation Request:", buildRequest());
  };

  const handleTimeframeChange = (newTimeframe: string) => {
    setTimeframe(newTimeframe);
  };

  const { stockData, strategies, tradesByStrategy, performanceCurves, isLoading, error } = data;

  const getTrades = (strategyName: string) => tradesByStrategy[strategyName] || [];
  const getPerformance = (strategyName: string) =>
    performanceCurves[strategyName] ? [{ name: strategyName, data: performanceCurves[strategyName] }] : [];

  const mapTradesToMarkers = (strategyTrades: Trade[]): MarkerPoint[] => {
    return strategyTrades.flatMap((t) => [
      {
        date: t.entryDate,
        price: t.entryPrice,
        type: "BUY",
        trade: t,
      },
      {
        date: t.exitDate,
        price: t.exitPrice,
        type: "SELL",
        trade: t,
      },
    ]);
  };

  const tradesA = strategyA ? getTrades(strategyA) : [];
  const tradesB = strategyB ? getTrades(strategyB) : [];
  const markerA = tradesA.length ? mapTradesToMarkers(tradesA) : [];
  const markerB = tradesB.length ? mapTradesToMarkers(tradesB) : [];
  console.log("Loaded strategies:", availableStrategies);

  return (
    <div className="simulation-root">
      <div className="simulation-paper">
        <SimulationControlBar
          strategies={availableStrategies.map(s => ({ name: s.strategyName }))}
          strategyA={strategyA}
          strategyB={strategyB}
          onStrategyAChange={setStrategyA}
          onStrategyBChange={setStrategyB}
          timeframe={timeframe}
          onTimeframeChange={handleTimeframeChange}
          symbol={symbol}
          onSymbolChange={setSymbol}
          onStart={handleStart}
        />

        <Typography variant="h6" className="simulation-title">Simulation results</Typography>
        <div className="simulation-content">
          {!simulationStarted ? (
            <div className="simulation-hint">Please select strategies, timeframe and symbol and start the simulation.</div>
          ) : isLoading ? (
            <div className="simulation-hint">Loading...</div>
          ) : error ? (
            <div className="simulation-hint">Error: {error}</div>
          ) : (
            <>
              <ChartSwitcher selectedChart={selectedChart} onChange={setSelectedChart} />
              <div className="charts-row">
                {selectedChart === 'price' && (
                  <>
                    <div className="chart-container">
                      <Typography variant="h4" align="center" className="chart-title-blue">{strategyA}</Typography>
                      <PriceChart
                        priceSeries={stockData.map((d) => ({ date: d.date, close: d.closePrice }))}
                        markers={markerA}
                        height={600}
                      />
                    </div>
                    <div className="chart-container">
                      <Typography variant="h4" align="center" className="chart-title-pink">{strategyB}</Typography>
                      <PriceChart
                        priceSeries={stockData.map((d) => ({ date: d.date, close: d.closePrice }))}
                        markers={markerB}
                        height={600}
                      />
                    </div>
                  </>
                )}
                {selectedChart === 'performance' && (
                  <>
                    <div className="chart-container">
                      <Typography variant="h4" align="center" className="chart-title-blue">{strategyA}</Typography>
                      <PerformanceChart strategies={getPerformance(strategyA)} height={600} />
                    </div>
                    <div className="chart-container">
                      <Typography variant="h4" align="center" className="chart-title-pink">{strategyB}</Typography>
                      <PerformanceChart strategies={getPerformance(strategyB)} height={600} />
                    </div>
                  </>
                )}
                {selectedChart === 'compare' && (
                  <div className="chart-container">
                    <StrategyCompareChart
                      strategies={strategies.map((s) => ({
                        name: s.strategyName,
                        totalProfit: s.totalProfit,
                        averageHoldingDays: s.averageHoldingDays,
                        tradeCount: s.tradeCount,
                        winRate: 0, // TODO: berechnen
                        maxDrawdown: 0, // TODO: berechnen
                      }))}
                      height={600}
                    />
                  </div>
                )}
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
}