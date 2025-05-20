import React, { useState } from 'react';
import { Typography } from '@mui/material';
import SimulationControlBar from '../components/controllbar/SimulationControlBar';
import ChartSwitcher, { ChartType } from '../components/charts/ChartSwitcher';
import EquityCurveChart from '../components/charts/EquityCurveChart';
import PriceChart from '../components/charts/PriceChart';
import StrategyCompareChart from '../components/charts/StrategyCompareChart';
import './SimulationPage.css';

// Test-Strategien (später durch echte Daten ersetzen)
const TEST_STRATEGIES = [
  {
    name: 'Test Strategy',
    entryStrategy: 'Momentum',
    exitStrategy: 'TargetProfit',
    entryParameters: { short: 10, long: 30 },
    exitParameters: { threshold: 0.05, multiplier: 2 },
  },
  {
    name: 'test3',
    entryStrategy: 'BuyTheDip',
    exitStrategy: 'TrailingStop',
    entryParameters: { short: 5, long: 15 },
    exitParameters: { threshold: 0.02, multiplier: 1.5 },
  },
];

// Neue Dummy-Daten für bessere Chart-Simulation
const priceSeries = [
  { date: "2024-04-01", close: 178.2 },
  { date: "2024-04-02", close: 176.9 },
  { date: "2024-04-03", close: 175.5 },
  { date: "2024-04-04", close: 177.8 },
  { date: "2024-04-05", close: 180.1 },
  { date: "2024-04-06", close: 181.3 },
  { date: "2024-04-07", close: 182.5 },
  { date: "2024-04-08", close: 180.8 },
  { date: "2024-04-09", close: 179.9 },
  { date: "2024-04-10", close: 180.4 },
  { date: "2024-04-11", close: 182.1 },
  { date: "2024-04-12", close: 183.7 },
  { date: "2024-04-13", close: 185.3 },
  { date: "2024-04-14", close: 184.6 },
  { date: "2024-04-15", close: 183.0 },
  { date: "2024-04-16", close: 182.7 },
  { date: "2024-04-17", close: 181.4 },
  { date: "2024-04-18", close: 179.5 },
  { date: "2024-04-19", close: 177.8 },
  { date: "2024-04-20", close: 178.6 },
  { date: "2024-04-21", close: 179.2 },
  { date: "2024-04-22", close: 180.4 },
  { date: "2024-04-23", close: 181.0 },
  { date: "2024-04-24", close: 182.3 },
  { date: "2024-04-25", close: 183.1 },
  { date: "2024-04-26", close: 184.0 },
  { date: "2024-04-27", close: 182.8 },
  { date: "2024-04-28", close: 181.6 },
  { date: "2024-04-29", close: 182.9 },
  { date: "2024-04-30", close: 184.4 },
];

const trades = [
  { date: "2024-04-05", type: "BUY" as const, price: 180.1 },
  { date: "2024-04-10", type: "SELL" as const, price: 180.4 },
  { date: "2024-04-15", type: "BUY" as const, price: 183.0 },
  { date: "2024-04-20", type: "SELL" as const, price: 178.6 },
  { date: "2024-04-26", type: "BUY" as const, price: 184.0 },
];

const movingAverages = {
  short: [
    { date: "2024-04-05", value: 177.7 },
    { date: "2024-04-06", value: 178.3 },
    { date: "2024-04-07", value: 179.4 },
    { date: "2024-04-08", value: 180.0 },
    { date: "2024-04-09", value: 180.2 },
    { date: "2024-04-10", value: 180.6 },
    { date: "2024-04-11", value: 180.9 },
    { date: "2024-04-12", value: 181.6 },
    { date: "2024-04-13", value: 182.6 },
    { date: "2024-04-14", value: 183.2 },
    { date: "2024-04-15", value: 183.9 },
    { date: "2024-04-16", value: 183.7 },
    { date: "2024-04-17", value: 183.0 },
    { date: "2024-04-18", value: 181.9 },
    { date: "2024-04-19", value: 180.4 },
    { date: "2024-04-20", value: 179.8 },
    { date: "2024-04-21", value: 179.3 },
    { date: "2024-04-22", value: 179.9 },
    { date: "2024-04-23", value: 180.6 },
    { date: "2024-04-24", value: 181.2 },
    { date: "2024-04-25", value: 182.0 },
    { date: "2024-04-26", value: 182.7 },
    { date: "2024-04-27", value: 183.0 },
    { date: "2024-04-28", value: 183.2 },
    { date: "2024-04-29", value: 183.6 },
    { date: "2024-04-30", value: 183.9 },
  ],
  long: [
    { date: "2024-04-20", value: 180.8 },
    { date: "2024-04-21", value: 180.9 },
    { date: "2024-04-22", value: 181.1 },
    { date: "2024-04-23", value: 181.3 },
    { date: "2024-04-24", value: 181.6 },
    { date: "2024-04-25", value: 181.9 },
    { date: "2024-04-26", value: 182.0 },
    { date: "2024-04-27", value: 182.2 },
    { date: "2024-04-28", value: 182.3 },
    { date: "2024-04-29", value: 182.5 },
    { date: "2024-04-30", value: 182.8 },
  ],
};

export default function SimulationPage() {
  const [strategyA, setStrategyA] = useState('');
  const [strategyB, setStrategyB] = useState('');
  const [timeframe, setTimeframe] = useState('01.01.2024-01.04.2024');
  const [symbol, setSymbol] = useState('AAPL');
  const [simulationStarted, setSimulationStarted] = useState(false);
  const [selectedChart, setSelectedChart] = useState<ChartType>('equity');

  // Platzhalterdaten für die Charts (später durch echte Simulationsdaten ersetzen)
  const dummyEquityA = [{ date: '2024-01-01', value: 10000 }, { date: '2024-02-01', value: 10500 }, { date: '2024-03-01', value: 11000 }, { date: '2024-04-01', value: 11500 }];
  const dummyEquityB = [{ date: '2024-01-01', value: 10000 }, { date: '2024-02-01', value: 10200 }, { date: '2024-03-01', value: 10800 }, { date: '2024-04-01', value: 11200 }];
  const dummyMetrics = [
    { name: 'Strategie A', totalProfit: 950, averageHoldingDays: 9.8, tradeCount: 10, winRate: 60, maxDrawdown: 5 },
    { name: 'Strategie B', totalProfit: 800, averageHoldingDays: 11.4, tradeCount: 10, winRate: 55, maxDrawdown: 7 },
  ];

  const handleStart = () => {
    setSimulationStarted(true);
  };

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
          onTimeframeChange={setTimeframe}
          symbol={symbol}
          onSymbolChange={setSymbol}
          onStart={handleStart}
        />

        <Typography variant="h6" className="simulation-title">
          Simulation results
        </Typography>
        <div className="simulation-content">
          {!simulationStarted ? (
            <div className="simulation-hint">Please select strategies, timeframe and symbol and start the simulation.</div>
          ) : (
            <>
              <ChartSwitcher selectedChart={selectedChart} onChange={setSelectedChart} />
              <div className="charts-row">
                {selectedChart === 'price' && (
                  <>
                    <div className="chart-container">
                      <Typography variant="h4" align="center" className="chart-title-blue">{strategyA || 'Strategie A'}</Typography>
                      <PriceChart priceSeries={priceSeries} trades={trades} movingAverages={movingAverages} height={600} />
                    </div>
                    <div className="chart-container">
                      <Typography variant="h4" align="center" className="chart-title-pink">{strategyB || 'Strategie B'}</Typography>
                      <PriceChart priceSeries={priceSeries} trades={trades} movingAverages={movingAverages} height={600} />
                    </div>
                  </>
                )}
                {selectedChart === 'equity' && (
                  <>
                    <div className="chart-container">
                      <Typography variant="h4" align="center" className="chart-title-blue">{strategyA || 'Strategie A'}</Typography>
                      <EquityCurveChart strategies={[{ name: strategyB || 'StrategieA', equityCurve: dummyEquityA }]} height={600} />
                    </div>
                    <div className="chart-container">
                      <Typography variant="h4" align="center" className="chart-title-pink">{strategyB || 'Strategie B'}</Typography>
                      <EquityCurveChart strategies={[{ name: strategyA || 'StrategieB', equityCurve: dummyEquityB }]} height={600} />
                    </div>
                  </>
                )}
                {selectedChart === 'compare' && (
                  <div className="chart-container">
                    <StrategyCompareChart
                      strategies={[
                        {
                          name: strategyA ? strategyA.replace(/\s+/g, '') : 'A',
                          totalProfit: strategyA === 'Test Strategy' ? 50 : 80,
                          averageHoldingDays: strategyA === 'Test Strategy' ? 9.8 : 12.1,
                          tradeCount: strategyA === 'Test Strategy' ? 150 : 70,
                          winRate: strategyA === 'Test Strategy' ? 60 : 45,
                          maxDrawdown: strategyA === 'Test Strategy' ? 5 : 12,
                        },
                        {
                          name: strategyB ? strategyB.replace(/\s+/g, '') : 'B',
                          totalProfit: strategyB === 'Test Strategy' ? 90 : 80,
                          averageHoldingDays: strategyB === 'Test Strategy' ? 9.8 : 11.4,
                          tradeCount: strategyB === 'Test Strategy' ? 100 : 50,
                          winRate: strategyB === 'Test Strategy' ? 60 : 30,
                          maxDrawdown: strategyB === 'Test Strategy' ? 5 : 15,
                        }
                      ]}
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