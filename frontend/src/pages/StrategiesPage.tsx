import React, { useState } from 'react';
import StrategyCreator from '../components/StrategyCreator';
import StrategyList from '../components/StrategyList';
import '../components/StrategyCreator.css';

export default function StrategiesPage() {
  const [strategies, setStrategies] = useState<any[]>([]);
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
        background: 'transparent',
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
          boxShadow: '0 2px 16px #0002',
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
          width: '100%',
          maxWidth: 800,
          background: '#181925',
          borderRadius: 16,
          padding: 24,
          boxShadow: '0 2px 16px #0002',
          boxSizing: 'border-box',
        }}
      >
        <StrategyList
          strategies={strategies}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>
    </div>
  );
} 