import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Chart from './components/Chart';
import { CssBaseline, Container } from '@mui/material';

function App() {
  const [count, setCount] = useState(0)

  return (
    <div>
      <CssBaseline />
      <Container>
        <Chart />
      </Container>
    </div>
  );
}

export default App
