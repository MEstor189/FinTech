import React from 'react';
import { Button, Typography, useMediaQuery, useTheme } from '@mui/material';
import './StrategyList.css';
import { StrategyConfigRequest } from '../../types/strategies';

interface StrategyListProps {
  strategies: StrategyConfigRequest[];
  onEdit: (strategy: StrategyConfigRequest) => void;
  onDelete: (strategy: StrategyConfigRequest) => void;
  wide?: boolean;
}

export default function StrategyList({ strategies, onEdit, onDelete, wide }: StrategyListProps) {
  const theme = useTheme();
  const isSmallScreen = useMediaQuery(theme.breakpoints.down('md'));

  return (
    <div className="strategy-list-root">
      <Typography variant="h6" className="strategy-list-title">
        Gespeicherte Strategien
      </Typography>

      {strategies.length === 0 && (
        <Typography variant="body2" className="strategy-list-empty">
          Noch keine Strategien vorhanden.
        </Typography>
      )}

      {strategies.map((strategy) => (
        <div
          key={strategy.strategyName}
          className={`strategy-list-item${wide ? ' wide' : ''}${isSmallScreen ? ' small' : ''}`}
        >
          <div className="strategy-list-main">
            <Typography className="strategy-list-name">
              {strategy.strategyName}
            </Typography>

            <div className="strategy-list-details">
              <div className="strategy-list-detail-block">
                <Typography className="strategy-list-detail-label">Entry</Typography>
                <Typography className="strategy-list-detail-value">
                  {strategy.entryStrategyName}
                </Typography>
              </div>

              <div className="strategy-list-detail-block">
                <Typography className="strategy-list-detail-label">Exit</Typography>
                <Typography className="strategy-list-detail-value">
                  {strategy.exitStrategyName}
                </Typography>
              </div>

              <div className="strategy-list-detail-block">
                <Typography className="strategy-list-detail-label">Entry Params</Typography>
                <div className="strategy-list-params">
                  {strategy.entryParams.map((p, i) => (
                    <span key={i} className="strategy-list-param">
                      {p.key}: {p.value}
                    </span>
                  ))}
                </div>
              </div>

              <div className="strategy-list-detail-block">
                <Typography className="strategy-list-detail-label">Exit Params</Typography>
                <div className="strategy-list-params">
                  {strategy.exitParams.map((p, i) => (
                    <span key={i} className="strategy-list-param">
                      {p.key}: {p.value}
                    </span>
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
              onClick={() => onEdit(strategy)}
            >
              Edit
            </Button>
            <Button
              variant="outlined"
              color="error"
              size="small"
              className="strategy-list-delete-btn"
              onClick={() => onDelete(strategy)}
            >
              Delete
            </Button>
          </div>
        </div>
      ))}
    </div>
  );
}