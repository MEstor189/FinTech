import React from 'react';
import { Button, Typography, useMediaQuery, useTheme } from '@mui/material';
import './StrategyList.css';

interface StrategyListProps {
  strategies: any[];
  onEdit: (index: number) => void;
  onDelete: (index: number) => void;
  wide?: boolean;
}

export default function StrategyList({ strategies, onEdit, onDelete, wide }: StrategyListProps) {
  const theme = useTheme();
  const isSmallScreen = useMediaQuery(theme.breakpoints.down('md'));

  return (
    <div className="strategy-list-root">
      <Typography variant="h6" className="strategy-list-title">
        Saved Strategies
      </Typography>
      {strategies.length === 0 && (
        <Typography variant="body2" className="strategy-list-empty">
          No strategies saved yet.
        </Typography>
      )}
      {strategies.map((strategy, idx) => (
        <div key={idx} className={`strategy-list-item${wide ? ' wide' : ''}${isSmallScreen ? ' small' : ''}`}>
          <div className="strategy-list-main">
            <Typography className="strategy-list-name">
              {strategy.name || `Strategy ${idx + 1}`}
            </Typography>
            <div className="strategy-list-details">
              <div className="strategy-list-detail-block">
                <Typography className="strategy-list-detail-label">Entry</Typography>
                <Typography className="strategy-list-detail-value">{strategy.entryStrategy}</Typography>
              </div>
              <div className="strategy-list-detail-block">
                <Typography className="strategy-list-detail-label">Exit</Typography>
                <Typography className="strategy-list-detail-value">{strategy.exitStrategy}</Typography>
              </div>
              <div className="strategy-list-detail-block">
                <Typography className="strategy-list-detail-label">Entry Params</Typography>
                <div className="strategy-list-params">
                  {(Object.entries(strategy.entryParameters || {}) as [string, string | number][]).map(([k, v]) => (
                    <span key={k} className="strategy-list-param">{k}: {v}</span>
                  ))}
                </div>
              </div>
              <div className="strategy-list-detail-block">
                <Typography className="strategy-list-detail-label">Exit Params</Typography>
                <div className="strategy-list-params">
                  {(Object.entries(strategy.exitParameters || {}) as [string, string | number][]).map(([k, v]) => (
                    <span key={k} className="strategy-list-param">{k}: {v}</span>
                  ))}
                </div>
              </div>
            </div>
          </div>
          <div className="strategy-list-actions">
            <Button
              variant="outlined"
              color="primary"
              size="small"
              className="strategy-list-edit-btn"
              onClick={() => onEdit(idx)}
            >
              Edit
            </Button>
            <Button
              variant="outlined"
              color="error"
              size="small"
              className="strategy-list-delete-btn"
              onClick={() => onDelete(idx)}
            >
              Delete
            </Button>
          </div>
        </div>
      ))}
    </div>
  );
} 