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

// Testdaten für UI-Steuerung
const TEST_STRATEGIES = [
  {
    name: 'Test Strategy',
    entryStrategy: 'MOMENTUM',
    exitStrategy: 'TARGET_PROFIT',
    entryParameters: { short: 10, long: 30 },
    exitParameters: { threshold: 0.05, multiplier: 2 },
  },
  {
    name: 'test3',
    entryStrategy: 'BUY_THE_DIP',
    exitStrategy: 'TRAILING_STOP',
    entryParameters: { short: 5, long: 15 },
    exitParameters: { threshold: 0.02, multiplier: 1.5 },
  },
];

export default function SimulationPage() {
  const [strategyA, setStrategyA] = useState('');
  const [strategyB, setStrategyB] = useState('');
  const [timeframe, setTimeframe] = useState('01.01.2024-01.04.2024');
  const [symbol, setSymbol] = useState('AAPL');
  const [simulationStarted, setSimulationStarted] = useState(false);
  const [selectedChart, setSelectedChart] = useState<ChartType>('equity');

  // Request nur bei gestarteter Simulation generieren
  const buildRequest = (): SimulationRequest | null => {
    const stratA = TEST_STRATEGIES.find((s) => s.name === strategyA);
    const stratB = TEST_STRATEGIES.find((s) => s.name === strategyB);
    if (!stratA || !stratB) return null;

    const [startDate, endDate] = timeframe.split('-').map((d) => {
      const [day, month, year] = d.trim().split('.');
      return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
    });

    return {
      symbol,
      startDate,
      endDate,
      investmentPerTrade: 100.0,
      requestStrategies: [
        {
          strategyName: stratA.name,
          entryStrategyType: stratA.entryStrategy,
          exitStrategyType: stratA.exitStrategy,
          entryParams: stratA.entryParameters,
          exitParams: stratA.exitParameters,
        },
        {
          strategyName: stratB.name,
          entryStrategyType: stratB.entryStrategy,
          exitStrategyType: stratB.exitStrategy,
          entryParams: stratB.entryParameters,
          exitParams: stratB.exitParameters,
        },
      ],
    };
  };

  const request = simulationStarted ? buildRequest() : null;
  const [data, refetch] = useSimulationData(request);

  const handleStart = () => {
    setSimulationStarted(true);
    refetch();
  };

  const handleTimeframeChange = (newTimeframe: string) => {
    setTimeframe(newTimeframe);
  };

  const { stockData, strategies, tradesByStrategy, performanceCurves, isLoading, error } = data;

  const getTrades = (strategyName: string) => tradesByStrategy[strategyName] || [];
  const getPerformance = (strategyName: string) =>
    performanceCurves[strategyName] ? [{ name: strategyName, data: performanceCurves[strategyName] }] : [];

  const mapTradesToMarkers = (strategyTrades: Trade[]): { date: string; type: "BUY" | "SELL"; price: number }[] => {
  return strategyTrades.flatMap((t) => [
    { date: t.entryDate, type: "BUY", price: t.entryPrice },
    { date: t.exitDate, type: "SELL", price: t.exitPrice },
  ]);
}

  return (
    <div className="simulation-root">
      <div className="simulation-paper">
        <SimulationControlBar
          strategies={TEST_STRATEGIES}
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
                        trades={mapTradesToMarkers(getTrades(strategyA))}
                        height={600}
                      />
                    </div>
                    <div className="chart-container">
                      <Typography variant="h4" align="center" className="chart-title-pink">{strategyB}</Typography>
                      <PriceChart
                        priceSeries={stockData.map((d) => ({ date: d.date, close: d.closePrice }))}
                        trades={mapTradesToMarkers(getTrades(strategyB))}
                        height={600}
                      />
                    </div>
                  </>
                )}
                {selectedChart === 'equity' && (
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