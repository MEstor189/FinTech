import { useState, useEffect } from 'react';
import {
  Box,
  TextField,
  Button,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from '@mui/material';
import './StrategyCreator.css';

interface StrategyCreatorProps {
  onStrategyCreate: (strategy: any) => void;
}


const getStrategyParameters = (strategy: string) => {
  switch (strategy) {
    case 'BuyTheDip':
      return { placeholder: 14, placeholder2: 0.02 };
    case 'MovingAverage':
      return { placeholder: 20, placeholder2: 0.05 };
    case 'Momentum':
      return { placeholder: 10, placeholder2: 0.03 };
    case 'TrailingStop':
      return { placeholder: 15, placeholder2: 0.04 };
    case 'TargetProfit':
      return { placeholder: 25, placeholder2: 0.06 };
    default:
      return {};
  }
};

export default function StrategyCreator({ onStrategyCreate }: StrategyCreatorProps) {
  const [strategyName, setStrategyName] = useState('');
  const [entryStrategy, setEntryStrategy] = useState('');
  const [exitStrategy, setExitStrategy] = useState('');
  const [entryParameters, setEntryParameters] = useState({});
  const [exitParameters, setExitParameters] = useState({});

  useEffect(() => {
    setEntryParameters(getStrategyParameters(entryStrategy));
  }, [entryStrategy]);

  useEffect(() => {
    setExitParameters(getStrategyParameters(exitStrategy));
  }, [exitStrategy]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onStrategyCreate({
      name: strategyName,
      entryStrategy,
      exitStrategy,
      entryParameters,
      exitParameters,
    });
  };

  return (
    <Box component="form" onSubmit={handleSubmit}>
      <Typography variant="h6" gutterBottom>
        Create new strategy
      </Typography>
      
      <div className="grid-container">
        <TextField
          fullWidth
          label="Strategyname"
          value={strategyName}
          onChange={(e) => setStrategyName(e.target.value)}
          required
        />
        
        <FormControl fullWidth required>
          <InputLabel>Entry-Strategy</InputLabel>
          <Select
            value={entryStrategy}
            label="Entry-Strategy"
            onChange={(e) => setEntryStrategy(e.target.value)}
          >
            <MenuItem value="BuyTheDip">BuyTheDip</MenuItem>
            <MenuItem value="MovingAverage">MovingAverage</MenuItem>
            <MenuItem value="Momentum">Momentum</MenuItem>
          </Select>
        </FormControl>

        <FormControl fullWidth required>
          <InputLabel>Exit-Strategy</InputLabel>
          <Select
            value={exitStrategy}
            label="Exit-Strategy"
            onChange={(e) => setExitStrategy(e.target.value)}
          >
            <MenuItem value="Moving Average">Moving Average</MenuItem>
            <MenuItem value="TrailingStop">TrailingStop</MenuItem>
            <MenuItem value="TargetProfit">TargetProfit</MenuItem>
          </Select>
        </FormControl>

        {entryStrategy && (
          <Box>
            <Typography variant="subtitle1">Entry-Parameter</Typography>
            {Object.entries(entryParameters).map(([key, value]) => (
              <TextField
                key={key}
                fullWidth
                type="number"
                label={key}
                value={value}
                placeholder={`Wird später geändert: ${key}`}
                onChange={(e) => setEntryParameters({
                  ...entryParameters,
                  [key]: Number(e.target.value),
                })}
              />
            ))}
          </Box>
        )}

        {exitStrategy && (
          <Box>
            <Typography variant="subtitle1">Exit-Parameter</Typography>
            {Object.entries(exitParameters).map(([key, value]) => (
              <TextField
                key={key}
                fullWidth
                type="number"
                label={key}
                value={value}
                placeholder={`Wird später geändert: ${key}`}
                onChange={(e) => setExitParameters({
                  ...exitParameters,
                  [key]: Number(e.target.value),
                })}
              />
            ))}
          </Box>
        )}

        <div className="full-width-item">
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
          >
            Create Strategy
          </Button>
        </div>
      </div>
    </Box>
  );
} 