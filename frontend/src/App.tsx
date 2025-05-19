import { Container, Typography, Paper } from '@mui/material';
import styled from '@emotion/styled';
import StrategyCreator from './components/StrategyCreator';

const StyledPaper = styled(Paper)`
  padding: 20px;
  height: 100%;
  min-height: 300px;
`;

function App() {
  const handleStrategyCreate = (strategy: any) => {
    console.log('Neue Strategie erstellt:', strategy);
    // Hier würde die Logik zum Speichern der Strategie implementiert werden
  };

  return (
    <Container maxWidth="sm" sx={{ py: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Strategy-Creation
      </Typography>
      <StyledPaper>
        <StrategyCreator onStrategyCreate={handleStrategyCreate} />
      </StyledPaper>
    </Container>
  );
}

export default App;
