import React, { useState } from 'react';
import {
  Box,
  Typography,
  Paper,
} from '@mui/material';
import SimulationControlBar from '../components/SimulationControlBar';
import ChartSwitcher, { ChartType } from '../components/charts/ChartSwitcher';
import EquityCurveChart from '../components/charts/EquityCurveChart';
import PriceChart from '../components/charts/PriceChart';
import StrategyCompareChart from '../components/charts/StrategyCompareChart';

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
  const dummyPrice = [
    { date: '2024-01-01', close: 150 },
    { date: '2024-02-01', close: 160 },
    { date: '2024-03-01', close: 170 },
    { date: '2024-04-01', close: 180 },
  ];
  const dummyTrades = [
    { date: '2024-01-15', type: 'BUY' as const, price: 152 },
    { date: '2024-03-10', type: 'SELL' as const, price: 175 },
  ];
  const dummyMetrics = [
    { name: 'Strategie A', totalProfit: 950, averageHoldingDays: 9.8, tradeCount: 10, winRate: 60, maxDrawdown: 5 },
    { name: 'Strategie B', totalProfit: 800, averageHoldingDays: 11.4, tradeCount: 10, winRate: 55, maxDrawdown: 7 },
  ];

  const handleStart = () => {
    setSimulationStarted(true);
  };

  return (
    <Box
      sx={{
        minHeight: '100vh',
        width: '100vw',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'flex-start',
        padding: '24px',
        boxSizing: 'border-box',
        background: '#181925',
      }}
    >
      <Paper
        elevation={3}
        sx={{
          width: '100%',
          maxWidth: 1200,
          background: '#23243a',
          borderRadius: 4,
          padding: 4,
          color: 'white',
        }}
      >
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

        <Typography variant="h6" sx={{ color: '#a259ff', mb: 3 }}>
          Simulationsergebnisse
        </Typography>
        <Box sx={{
          minHeight: 420,
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'flex-start',
          alignItems: 'stretch',
        }}>
          {!simulationStarted ? (
            'Bitte wählen Sie Strategien, Zeitraum und Symbol und starten Sie die Simulation.'
          ) : (
            <>
              <ChartSwitcher selectedChart={selectedChart} onChange={setSelectedChart} />
              <Box sx={{ display: 'flex', gap: 4 }}>
                {selectedChart === 'equity' && (
                  <>
                    <Box sx={{ flex: 1 }}>
                      <Typography variant="h6" sx={{ color: '#5a6aff', mb: 1 }}>Strategie2</Typography>
                      <EquityCurveChart strategies={[{ name: 'Strategie2', equityCurve: dummyEquityA }]} />
                    </Box>
                    <Box sx={{ flex: 1 }}>
                      <Typography variant="h6" sx={{ color: '#ff4fd8', mb: 1 }}>Strategie1</Typography>
                      <EquityCurveChart strategies={[{ name: 'Strategie1', equityCurve: dummyEquityB }]} />
                    </Box>
                  </>
                )}
                {selectedChart === 'price' && (
                  <>
                    <Box sx={{ flex: 1 }}>
                      <Typography variant="h6" sx={{ color: '#5a6aff', mb: 1 }}>Strategie2</Typography>
                      <PriceChart priceSeries={dummyPrice} trades={dummyTrades} />
                    </Box>
                    <Box sx={{ flex: 1 }}>
                      <Typography variant="h6" sx={{ color: '#ff4fd8', mb: 1 }}>Strategie1</Typography>
                      <PriceChart priceSeries={dummyPrice} trades={dummyTrades} />
                    </Box>
                  </>
                )}
                {selectedChart === 'compare' && (
                  <Box sx={{ flex: 1 }}>
                    <StrategyCompareChart strategies={dummyMetrics} />
                  </Box>
                )}
              </Box>
            </>
          )}
        </Box>
      </Paper>
    </Box>
  );
} 