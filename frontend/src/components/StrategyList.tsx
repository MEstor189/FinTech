import React from 'react';
import { Button, Box, Typography, useMediaQuery, useTheme } from '@mui/material';

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
    <div style={{ maxWidth: '100vw', overflowX: 'auto' }}>
      <Typography variant="h6" sx={{ mb: 2, color: '#a259ff', textAlign: 'center', fontWeight: 700, fontSize: '2rem' }}>
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
            alignItems: wide ? 'stretch' : 'center',
            justifyContent: 'space-between',
            background: '#23243a',
            borderRadius: 2,
            p: 1,
            px: 3,
            mb: 2,
            flexDirection: wide && !isSmallScreen ? 'row' : 'column',
            gap: wide && !isSmallScreen ? 3 : 1.5,
            minHeight: 60,
          }}
        >
          <Box sx={{ flex: 2, display: 'flex', flexDirection: wide && !isSmallScreen ? 'row' : 'column', gap: wide && !isSmallScreen ? 3 : 1.5, alignItems: wide && !isSmallScreen ? 'center' : 'flex-start' }}>
            <Typography sx={{ fontWeight: 700, minWidth: 160, color: '#a259ff', fontSize: 20 }}>
              {strategy.name || `Strategy ${idx + 1}`}
            </Typography>
            <Box sx={{ display: 'flex', flexDirection: wide && !isSmallScreen ? 'row' : 'column', gap: wide && !isSmallScreen ? 3 : 1.5, alignItems: wide && !isSmallScreen ? 'center' : 'flex-start', ml: wide && !isSmallScreen ? 2 : 0 }}>
              <Box sx={{ pr: 3, borderRight: wide && !isSmallScreen ? '1px solid #333' : 'none', pb: wide && !isSmallScreen ? 0 : 1.5 }}>
                <Typography sx={{ color: '#a259ff', fontWeight: 600, fontSize: 15 }}>Entry</Typography>
                <Typography sx={{ color: '#fff', fontSize: 16 }}>{strategy.entryStrategy}</Typography>
              </Box>
              <Box sx={{ pr: 3, borderRight: wide && !isSmallScreen ? '1px solid #333' : 'none', pl: wide && !isSmallScreen ? 3 : 0, pb: wide && !isSmallScreen ? 0 : 1.5 }}>
                <Typography sx={{ color: '#a259ff', fontWeight: 600, fontSize: 15 }}>Exit</Typography>
                <Typography sx={{ color: '#fff', fontSize: 16 }}>{strategy.exitStrategy}</Typography>
              </Box>
              <Box sx={{ pr: 3, borderRight: wide && !isSmallScreen ? '1px solid #333' : 'none', pl: wide && !isSmallScreen ? 3 : 0, pb: wide && !isSmallScreen ? 0 : 1.5 }}>
                <Typography sx={{ color: '#a259ff', fontWeight: 600, fontSize: 15 }}>Entry Params</Typography>
                <Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, flexWrap: 'wrap' }}>
                  {(Object.entries(strategy.entryParameters || {}) as [string, string | number][]).map(([k, v]) => (
                    <span key={k} style={{ color: '#fff', fontSize: 16, background: '#181925', borderRadius: 4, padding: '4px 12px', marginRight: 8, marginBottom: 4 }}>{k}: {v}</span>
                  ))}
                </Box>
              </Box>
              <Box sx={{ pl: wide && !isSmallScreen ? 3 : 0 }}>
                <Typography sx={{ color: '#a259ff', fontWeight: 600, fontSize: 15 }}>Exit Params</Typography>
                <Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, flexWrap: 'wrap' }}>
                  {(Object.entries(strategy.exitParameters || {}) as [string, string | number][]).map(([k, v]) => (
                    <span key={k} style={{ color: '#fff', fontSize: 16, background: '#181925', borderRadius: 4, padding: '4px 12px', marginRight: 8, marginBottom: 4 }}>{k}: {v}</span>
                  ))}
                </Box>
              </Box>
            </Box>
          </Box>
          <Box sx={{ flex: 1, display: 'flex', justifyContent: wide && !isSmallScreen ? 'flex-end' : 'flex-start', gap: 1, alignItems: 'center', alignSelf: 'center', mt: wide && !isSmallScreen ? 0 : 2 }}>
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