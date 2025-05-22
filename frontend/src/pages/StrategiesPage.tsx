import React, { useState } from 'react';
import StrategyCreator from '../components/strategyCreator/StrategyCreator';
import StrategyList from '../components/strategyList/StrategyList';
import './StrategiesPage.css';

const TEST_STRATEGY = {
  name: 'Test Strategy',
  entryStrategy: 'MOMENTUM',
  exitStrategy: 'TARGET_PROFIT',
  entryParameters: { short: 10, long: 30 },
  exitParameters: { threshold: 0.05, multiplier: 2 },
};

const TEST_STRATEGY_2 = {
  name: 'test3',
  entryStrategy: 'BUY_THE_DIP',
  exitStrategy: 'TRAILING_STOP',
  entryParameters: { short: 5, long: 15 },
  exitParameters: { threshold: 0.02, multiplier: 1.5 },
};

export default function StrategiesPage() {
  const [strategies, setStrategies] = useState<any[]>([TEST_STRATEGY, TEST_STRATEGY_2]);
  const [editIndex, setEditIndex] = useState<number | null>(null);

  const handleCreate = (strategy: any) => {
    if (editIndex !== null) {
      // Update mode
      const updated = [...strategies];
      updated[editIndex] = strategy;
      setStrategies(updated);
      setEditIndex(null);
    } else {
      // Add new
      setStrategies([...strategies, strategy]);
    }
  };

  const handleEdit = (index: number) => {
    setEditIndex(index);
  };

  const handleDelete = (index: number) => {
    setStrategies(strategies.filter((_, i) => i !== index));
    if (editIndex === index) setEditIndex(null);
  };

  return (
    <div
      style={{
        minHeight: '100vh',
        width: '100vw',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'flex-start',
        padding: '24px 0',
        boxSizing: 'border-box',
        background: '#181925',
      }}
    >
      <div
        style={{
          width: '100%',
          maxWidth: 800,
          background: '#181925',
          borderRadius: 16,
          padding: 32,
          marginBottom: 32,
          boxSizing: 'border-box',
        }}
      >
        <StrategyCreator
          onStrategyCreate={handleCreate}
          editStrategy={editIndex !== null ? strategies[editIndex] : null}
          isEditing={editIndex !== null}
          onCancelEdit={() => setEditIndex(null)}
        />
      </div>
      <div
        style={{
          width: '100vw',
          background: '#181925',
          borderRadius: 16,
          padding: 24,

          boxSizing: 'border-box',
        }}
      >
        <StrategyList
          strategies={strategies}
          onEdit={handleEdit}
          onDelete={handleDelete}
          wide
        />
      </div>
    </div>
  );
} 