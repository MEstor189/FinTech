import React from 'react';
import { Box, FormControl, InputLabel, Select, MenuItem, TextField, Button } from '@mui/material';

interface SimulationControlBarProps {
  strategies: { name: string }[];
  strategyA: string;
  strategyB: string;
  onStrategyAChange: (value: string) => void;
  onStrategyBChange: (value: string) => void;
  timeframe: string;
  onTimeframeChange: (value: string) => void;
  symbol: string;
  onSymbolChange: (value: string) => void;
  onStart: () => void;
}

export default function SimulationControlBar({
  strategies,
  strategyA,
  strategyB,
  onStrategyAChange,
  onStrategyBChange,
  timeframe,
  onTimeframeChange,
  symbol,
  onSymbolChange,
  onStart,
}: SimulationControlBarProps) {
  return (
    <Box sx={{
      display: 'flex',
      gap: 2,
      alignItems: 'flex-end',
      flexWrap: 'wrap',
      mb: 4,
    }}>
      <FormControl sx={{ minWidth: 180 }}>
        <InputLabel sx={{ color: '#a259ff' }}>Strategie A</InputLabel>
        <Select
          value={strategyA}
          onChange={e => onStrategyAChange(e.target.value)}
          label="Strategie A"
          sx={{ color: 'white' }}
        >
          {strategies.map((s, i) => (
            <MenuItem key={i} value={s.name}>{s.name}</MenuItem>
          ))}
        </Select>
      </FormControl>
      <FormControl sx={{ minWidth: 180 }}>
        <InputLabel sx={{ color: '#a259ff' }}>Strategie B</InputLabel>
        <Select
          value={strategyB}
          onChange={e => onStrategyBChange(e.target.value)}
          label="Strategie B"
          sx={{ color: 'white' }}
        >
          {strategies.map((s, i) => (
            <MenuItem key={i} value={s.name}>{s.name}</MenuItem>
          ))}
        </Select>
      </FormControl>
      <TextField
        label="Zeitraum"
        value={timeframe}
        onChange={e => onTimeframeChange(e.target.value)}
        placeholder="DD.MM.YYYY-DD.MM.YYYY"
        sx={{ minWidth: 220 }}
        InputLabelProps={{ style: { color: '#a259ff' } }}
      />
      <TextField
        label="Symbol"
        value={symbol}
        onChange={e => onSymbolChange(e.target.value)}
        placeholder="AAPL"
        sx={{ minWidth: 120 }}
        InputLabelProps={{ style: { color: '#a259ff' } }}
      />
      <Button
        variant="contained"
        sx={{
          background: '#d72660',
          color: 'white',
          fontWeight: 600,
          minWidth: 180,
          height: 56,
          ml: 1,
        }}
        onClick={onStart}
      >
        Simulation starten
      </Button>
    </Box>
  );
} 