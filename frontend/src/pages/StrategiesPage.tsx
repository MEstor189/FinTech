import React, { useState } from 'react';
import StrategyCreator from '../components/strategyCreator/StrategyCreator';
import StrategyList from '../components/strategyList/StrategyList';
import './StrategiesPage.css';
import { useAvailableStrategies } from '../hooks/useAvailableStrategies';
import { StrategyConfigRequest } from '../types/strategies';
import { Box } from '@mui/material';
import StrategyTypeInfoList from '../components/strategyInfo/StrategyTypeInfoList';
import { deleteStrategy, updateStrategy } from '../hooks/useStrategyAction';

export default function StrategiesPage() {
  const [editStrategy, setEditStrategy] = useState<StrategyConfigRequest | null>(null);
  const { strategies: allStrategies, refetch } = useAvailableStrategies();

  const handleCreated = () => {
    setEditStrategy(null);
    refetch();
  };

  const handleEdit = (strategy: StrategyConfigRequest) => {
    setEditStrategy(strategy);
  };

  const handleDelete = async (strategy: StrategyConfigRequest) => {
  try {
    await deleteStrategy(strategy.id);
    refetch();
  } catch (err) {
    console.error("Fehler beim Löschen:", err);
  }
};

  return (
    <div id='main-container'>
      <Box display="flex" width="100%" justifyContent="space-between">
        <Box flex={1} width="25%" height="auto">
          <StrategyTypeInfoList direction="entry" />
        </Box>

        <Box flex={2} width="50%">
          <StrategyCreator
            onCreated={handleCreated}
            editStrategy={editStrategy}
            isEditing={!!editStrategy}
            onCancel={() => setEditStrategy(null)}
          />
        </Box>

        <Box flex={1} width="25%" height="auto">
          <StrategyTypeInfoList direction="exit" />
        </Box>
      </Box>

      <div id='list'>
        <StrategyList
          strategies={allStrategies}
          onEdit={handleEdit}
          onDelete={handleDelete}
          wide={true}
        />
      </div>
    </div>
  );
}
