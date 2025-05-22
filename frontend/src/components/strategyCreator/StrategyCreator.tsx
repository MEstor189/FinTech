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
import React from "react";

interface StrategyCreatorProps {
  onStrategyCreate: (strategy: any) => void;
  editStrategy?: any;
  isEditing?: boolean;
  onCancelEdit?: () => void;
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

export default function StrategyCreator({ onStrategyCreate, editStrategy, isEditing, onCancelEdit }: StrategyCreatorProps) {
  const [strategyName, setStrategyName] = useState('');
  const [entryStrategy, setEntryStrategy] = useState('');
  const [exitStrategy, setExitStrategy] = useState('');
  const [entryParameters, setEntryParameters] = useState<any>({});
  const [exitParameters, setExitParameters] = useState<any>({});

  // Prefill fields when editing
  useEffect(() => {
    if (isEditing && editStrategy) {
      setStrategyName(editStrategy.name || '');
      setEntryStrategy(editStrategy.entryStrategy || '');
      setExitStrategy(editStrategy.exitStrategy || '');
      setEntryParameters(editStrategy.entryParameters || {});
      setExitParameters(editStrategy.exitParameters || {});
    } else {
      setStrategyName('');
      setEntryStrategy('');
      setExitStrategy('');
      setEntryParameters({});
      setExitParameters({});
    }
  }, [isEditing, editStrategy]);

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
    <div className="strategy-card">
      <form className="grid-container" onSubmit={handleSubmit}>
        {/* 1st row: Name */}
        <div style={{ marginBottom: 0 }}>
          <Typography className="strategy-title" variant="subtitle1" sx={{ mb: 1 }}>
            Strategy Name
          </Typography>
          <TextField
            fullWidth
            placeholder="Enter strategy name..."
            value={strategyName}
            onChange={(e) => setStrategyName(e.target.value)}
            required
          />
        </div>
        {/* 2nd row: Entry/Exit selection side by side */}
        <div style={{ display: 'flex', gap: 16, alignItems: 'flex-end' }}>
          <div style={{ flex: 1 }}>
            <Typography className="strategy-subtitle" variant="subtitle2" sx={{ mb: 1 }}>
              Entry Strategy
            </Typography>
            <FormControl fullWidth required className="input-label-lila">
              <Select
                displayEmpty
                value={entryStrategy}
                onChange={(e) => setEntryStrategy(e.target.value)}
                renderValue={selected => selected ? selected : 'Select entry strategy...'}
              >
                <MenuItem value="" disabled>Select entry strategy...</MenuItem>
                <MenuItem value="BuyTheDip">BuyTheDip</MenuItem>
                <MenuItem value="MovingAverage">MovingAverage</MenuItem>
                <MenuItem value="Momentum">Momentum</MenuItem>
              </Select>
            </FormControl>
          </div>
          <div style={{ flex: 1 }}>
            <Typography className="strategy-subtitle" variant="subtitle2" sx={{ mb: 1 }}>
              Exit Strategy
            </Typography>
            <FormControl fullWidth required className="input-label-lila">
              <Select
                displayEmpty
                value={exitStrategy}
                onChange={(e) => setExitStrategy(e.target.value)}
                renderValue={selected => selected ? selected : 'Select exit strategy...'}
              >
                <MenuItem value="" disabled>Select exit strategy...</MenuItem>
                <MenuItem value="Moving Average">Moving Average</MenuItem>
                <MenuItem value="TrailingStop">TrailingStop</MenuItem>
                <MenuItem value="TargetProfit">TargetProfit</MenuItem>
              </Select>
            </FormControl>
          </div>
        </div>
        {/* 3rd row: Entry parameters side by side, max. 4 fields */}
        <div style={{ marginTop: 8 }}>
          <Typography className="strategy-subtitle" variant="subtitle1" sx={{ mb: 1 }}>
            Entry Parameters
          </Typography>
          <div style={{ display: 'flex', gap: 16, minHeight: 80 }}>
            {[0,1,2,3].map((i) => {
              const entryKeys = Object.keys(entryParameters);
              const key = entryKeys[i];
              // Placeholder names for parameters
              const paramNames = ['ShortPeriod', 'LongPeriod', 'Threshold', 'Multiplier'];
              return key ? (
                <div key={key} style={{ flex: 1, display: 'flex', flexDirection: 'column', gap: 4 }}>
                  <Typography className="strategy-subtitle" variant="caption" sx={{ mb: 0 }}>
                    {paramNames[i]}
                  </Typography>
                  <TextField
                    fullWidth
                    type="number"
                    value={entryParameters[key]}
                    placeholder={`Will be changed later: ${key}`}
                    onChange={(e) => setEntryParameters({
                      ...entryParameters,
                      [key]: Number(e.target.value),
                    })}
                  />
                </div>
              ) : (
                <div key={i} style={{ flex: 1 }} />
              );
            })}
          </div>
        </div>
        {/* 4th row: Exit parameters side by side, max. 4 fields */}
        <div style={{ marginTop: 8 }}>
          <Typography className="strategy-subtitle" variant="subtitle1" sx={{ mb: 1 }}>
            Exit Parameters
          </Typography>
          <div style={{ display: 'flex', gap: 16, minHeight: 80 }}>
            {[0,1,2,3].map((i) => {
              const exitKeys = Object.keys(exitParameters);
              const key = exitKeys[i];
              // Placeholder names for parameters
              const paramNames = ['ShortPeriod', 'LongPeriod', 'Threshold', 'Multiplier'];
              return key ? (
                <div key={key} style={{ flex: 1, display: 'flex', flexDirection: 'column', gap: 4 }}>
                  <Typography className="strategy-subtitle" variant="caption" sx={{ mb: 0 }}>
                    {paramNames[i]}
                  </Typography>
                  <TextField
                    fullWidth
                    type="number"
                    value={exitParameters[key]}
                    placeholder={`Will be changed later: ${key}`}
                    onChange={(e) => setExitParameters({
                      ...exitParameters,
                      [key]: Number(e.target.value),
                    })}
                  />
                </div>
              ) : (
                <div key={i} style={{ flex: 1 }} />
              );
            })}
          </div>
        </div>
        {/* Buttons */}
        <div className="full-width-item" style={{ display: 'flex', gap: 12, marginTop: 16 }}>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            className="save-btn"
            sx={{ background: isEditing ? undefined : undefined }}
          >
            {isEditing ? 'Update Strategy' : 'Save Strategy'}
          </Button>
          {isEditing && onCancelEdit && (
            <Button
              variant="contained"
              color="primary"
              fullWidth
              className="cancel-btn"
              onClick={onCancelEdit}
            >
              Cancel
            </Button>
          )}
        </div>
      </form>
    </div>
  );
} 