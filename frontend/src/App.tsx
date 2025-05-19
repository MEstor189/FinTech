import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import StrategiesPage from './pages/StrategiesPage';
import SimulationPage from './pages/SimulationPage';
import InformationPage from './pages/InformationPage';

function App() {
  return (
    <Router>
      <Navbar />
      <div style={{ paddingTop: 72 }}>
        <Routes>
          <Route path="/strategies" element={<StrategiesPage />} />
          <Route path="/simulation" element={<SimulationPage />} />
          <Route path="/information" element={<InformationPage />} />
          <Route path="/" element={<Navigate to="/simulation" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
