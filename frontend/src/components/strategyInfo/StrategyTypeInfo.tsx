import { Box, Typography, Divider } from '@mui/material';
import { StrategyTypeWithParams } from '../../types/strategies';
import './StrategyTypeInfo.css';

interface StrategyTypeInfoProps {
  strategy: StrategyTypeWithParams;
}

export default function StrategyTypeInfo({ strategy }: StrategyTypeInfoProps) {
  return (
    <Box className="strategy-type-box">
      <Typography className="strategy-type-title">{strategy.name}</Typography>

      {strategy.description && (
        <Typography className="strategy-type-description">
          {strategy.description}
        </Typography>
      )}

      {strategy.params.length > 0 && (
        <>
          <Divider sx={{ my: 1 }} />
          <Typography variant="subtitle2">Parameter:</Typography>
          <Box className="strategy-type-param-group">
            {strategy.params.map((param, index) => (
              <Box key={index} className="strategy-type-param">
                <Typography className="strategy-type-param-label">
                  • <strong>{param.key}</strong> ({param.inputType} / {param.type})
                </Typography>
                {param.description && (
                  <span className="strategy-type-param-desc">
                    {param.description}
                  </span>
                )}
              </Box>
            ))}
          </Box>
        </>
      )}
    </Box>
  );
}