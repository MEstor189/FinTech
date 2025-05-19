import React from 'react';
import { Button, Box, Typography } from '@mui/material';

interface StrategyListProps {
  strategies: any[];
  onEdit: (index: number) => void;
  onDelete: (index: number) => void;
}

export default function StrategyList({ strategies, onEdit, onDelete }: StrategyListProps) {
  return (
    <div>
      <Typography variant="h6" sx={{ mb: 2 }}>
        Saved Strategies
      </Typography>
      {strategies.length === 0 && (
        <Typography variant="body2" color="grey.500">
          No strategies saved yet.
        </Typography>
      )}
      {strategies.map((strategy, idx) => (
        <Box
          key={idx}
          sx={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            background: '#23243a',
            borderRadius: 2,
            p: 2,
            mb: 2,
          }}
        >
          <Typography sx={{ fontWeight: 500 }}>{strategy.name || `Strategy ${idx + 1}`}</Typography>
          <Box>
            <Button
              variant="outlined"
              color="primary"
              size="small"
              sx={{ mr: 1, borderRadius: 2, minWidth: 70 }}
              onClick={() => onEdit(idx)}
            >
              Edit
            </Button>
            <Button
              variant="outlined"
              color="error"
              size="small"
              sx={{ borderRadius: 2, minWidth: 70 }}
              onClick={() => onDelete(idx)}
            >
              Delete
            </Button>
          </Box>
        </Box>
      ))}
    </div>
  );
} 