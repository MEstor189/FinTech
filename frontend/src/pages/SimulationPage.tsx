import React, { useState } from 'react';
import {
  Box,
  Typography,
  Paper,
} from '@mui/material';
import SimulationControlBar from '../components/SimulationControlBar';

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

        {/* Hier können weitere Simulationsinhalte folgen */}
        <Typography variant="h6" sx={{ color: '#a259ff', mb: 3 }}>
          Simulationsergebnisse
        </Typography>
        <Box sx={{ 
          display: 'flex', 
          justifyContent: 'center', 
          alignItems: 'center',
          height: 'calc(100% - 100px)',
          color: '#666'
        }}>
          {!simulationStarted ? (
            'Bitte wählen Sie Strategien, Zeitraum und Symbol und starten Sie die Simulation.'
          ) : (
            <Box sx={{ textAlign: 'center' }}>
              <Typography variant="body1" sx={{ mb: 2 }}>
                Strategie A: {strategyA || '-'}<br />
                Strategie B: {strategyB || '-'}
              </Typography>
              <Typography variant="body2">
                Zeitraum: {timeframe}
              </Typography>
              <Typography variant="body2">
                Symbol: {symbol}
              </Typography>
            </Box>
          )}
        </Box>
      </Paper>
    </Box>
  );
} 