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
import './Navbar.css';

const tabRoutes = ['/simulation', '/strategies', '/information'];

export default function Navbar() {
  const location = useLocation();
  const navigate = useNavigate();


  const currentTab = React.useMemo(() => {
    const idx = tabRoutes.findIndex((route) => location.pathname.startsWith(route));
    return idx === -1 ? 1 : idx; // Default: Strategies
  }, [location.pathname]);

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    navigate(tabRoutes[newValue]);
  };

  return (
    <AppBar position="fixed" className="navbar-root" elevation={1}>
      <Toolbar className="navbar-toolbar">
        <Box className="navbar-logo-group">
          <HexagonIcon sx={{ color: 'purple', fontSize: 32 }} />
          <Typography variant="h6" className="navbar-title">
            FinTech
          </Typography>
        </Box>

        <Tabs
          value={currentTab}
          onChange={handleTabChange}
          textColor="inherit"
          TabIndicatorProps={{ className: 'navbar-tab-indicator' }}
          className="navbar-tabs"
        >
          <Tab label="Simulation" className="navbar-tab" />
          <Tab label="Strategies" className="navbar-tab" />
          <Tab label="Information" className="navbar-tab" />
        </Tabs>

        <IconButton edge="end" className="navbar-icon">
          <MenuIcon />
        </IconButton>
      </Toolbar>
    </AppBar>
  );
} 