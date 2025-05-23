import React from 'react';
import { FormControl, InputLabel, Select, MenuItem, TextField, Button } from '@mui/material';
import './SimulationControlBar.css';

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
    <div className="sim-ctrl-bar">
      <FormControl className="sim-ctrl-select">
        <InputLabel className="sim-ctrl-label">Strategie A</InputLabel>
        <Select
          value={strategyA || ''}
          onChange={e => onStrategyAChange(e.target.value)}
          label="Strategie A"
          className="sim-ctrl-select-input"
        >
          {strategies.map((s, i) => (
            <MenuItem key={i} value={s.name}>{s.name}</MenuItem>
          ))}
        </Select>
      </FormControl>
      <FormControl className="sim-ctrl-select">
        <InputLabel className="sim-ctrl-label">Strategie B</InputLabel>
        <Select
          value={strategyB || ''}
          onChange={e => onStrategyBChange(e.target.value)}
          label="Strategie B"
          className="sim-ctrl-select-input"
        >
          {strategies.map((s, i) => (
            <MenuItem key={i} value={s.name}>{s.name}</MenuItem>
          ))}
        </Select>
      </FormControl>
      <TextField
        label="Timeframe"
        value={timeframe}
        onChange={e => onTimeframeChange(e.target.value)}
        placeholder="DD.MM.YYYY-DD.MM.YYYY"
        className="sim-ctrl-timeframe"
        InputLabelProps={{ className: 'sim-ctrl-label' }}
      />
      <TextField
        label="Symbol"
        value={symbol}
        onChange={e => onSymbolChange(e.target.value)}
        placeholder="AAPL"
        className="sim-ctrl-symbol"
        InputLabelProps={{ className: 'sim-ctrl-label' }}
      />
      <Button
        variant="contained"
        className="sim-ctrl-btn"
        onClick={onStart}
      >
        Start simulation
      </Button>
    </div>
  );
} 