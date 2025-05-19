import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Box from '@mui/material/Box';
import HexagonIcon from '@mui/icons-material/Hexagon';
import { useLocation, useNavigate } from 'react-router-dom';

const tabRoutes = ['/simulation', '/strategies', '/information'];

export default function Navbar() {
  const location = useLocation();
  const navigate = useNavigate();

  // Finde den aktiven Tab anhand der aktuellen Route
  const currentTab = React.useMemo(() => {
    const idx = tabRoutes.findIndex((route) => location.pathname.startsWith(route));
    return idx === -1 ? 1 : idx; // Default: Strategies
  }, [location.pathname]);

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    navigate(tabRoutes[newValue]);
  };

  return (
    <AppBar
      position="fixed"
      color="transparent"
      elevation={1}
      sx={{
        background: '#13141a',
        width: '100vw',
        left: 0,
        top: 0,
        borderRadius: 0,
        m: 0,
        zIndex: 1201,
      }}
    >
      <Toolbar sx={{ justifyContent: 'space-between', minHeight: 64 }}>
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
          <HexagonIcon sx={{ color: 'purple', fontSize: 32 }} />
          <Typography variant="h6" sx={{ fontWeight: 700, color: 'white', letterSpacing: 1 }}>
            FinTech
          </Typography>
        </Box>
        <Tabs
          value={currentTab}
          onChange={handleTabChange}
          textColor="inherit"
          TabIndicatorProps={{ style: { background: 'linear-gradient(90deg, #a259ff, #3772ff)' } }}
          sx={{
            '.MuiTab-root': { color: 'white', fontWeight: 500, fontSize: 16, minWidth: 120 },
            '.Mui-selected': { color: '#a259ff !important' },
            mr: 2
          }}
        >
          <Tab label="Simulation" />
          <Tab label="Strategies" />
          <Tab label="Information" />
        </Tabs>
        <IconButton edge="end" sx={{ color: 'white' }}>
          <MenuIcon />
        </IconButton>
      </Toolbar>
    </AppBar>
  );
} 