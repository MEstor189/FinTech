import React, { useState, useEffect } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  CircularProgress,
} from "@mui/material";
import { useStrategyTypes } from "../../hooks/useStrategyTypes";
import { useCreateStrategy } from "../../hooks/useCreateStrategy";
import { StrategyTypeWithParams, ParamKey, StrategyConfigRequest } from "../../types/strategies";
import "./StrategyCreator.css";
import { updateStrategy } from "../../hooks/useStrategyAction";

interface StrategyCreatorProps {
  onCreated: () => void;
  editStrategy?: StrategyConfigRequest | null;
  isEditing?: boolean;
  onCancel?: () => void;
}

export default function StrategyCreator({ onCreated, editStrategy, isEditing, onCancel }: StrategyCreatorProps) {
  const { data, isLoading: loadingTypes } = useStrategyTypes();
  const { create, isLoading: isSubmitting, error, success } = useCreateStrategy();

  const [strategyName, setStrategyName] = useState(editStrategy?.strategyName || "");
  const [selectedEntry, setSelectedEntry] = useState<StrategyTypeWithParams | null>(null);
  const [selectedExit, setSelectedExit] = useState<StrategyTypeWithParams | null>(null);
  const [entryParams, setEntryParams] = useState<Record<string, string>>({});
  const [exitParams, setExitParams] = useState<Record<string, string>>({});

  useEffect(() => {
    if (editStrategy && data) {
      setStrategyName(editStrategy.strategyName);
      
      const entryType = data.entryStrategies.find(s => s.name === editStrategy.entryStrategyName);
      const exitType = data.exitStrategies.find(s => s.name === editStrategy.exitStrategyName);
      
      if (entryType) {
        setSelectedEntry(entryType);
        const entryParamsMap: Record<string, string> = {};
        editStrategy.entryParams.forEach(p => {
          entryParamsMap[p.key] = p.value;
        });
        setEntryParams(entryParamsMap);
      }
      
      if (exitType) {
        setSelectedExit(exitType);
        const exitParamsMap: Record<string, string> = {};
        editStrategy.exitParams.forEach(p => {
          exitParamsMap[p.key] = p.value;
        });
        setExitParams(exitParamsMap);
      }
    }
  }, [editStrategy, data]);

  const handleParamChange = (
    type: "entry" | "exit",
    key: string,
    value: string
  ) => {
    if (type === "entry") {
      setEntryParams({ ...entryParams, [key]: value });
    } else {
      setExitParams({ ...exitParams, [key]: value });
    }
  };

  const buildParamValues = (params: ParamKey[], values: Record<string, string>) => {
    return params.map((param) => ({
      paramKeyId: param.id,
      value: values[param.key],
    }));
  };

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();
  if (!selectedEntry || !selectedExit) return;

  const request = {
    name: strategyName,
    entryStrategyTypeId: selectedEntry.id,
    exitStrategyTypeId: selectedExit.id,
    paramValues: [
      ...buildParamValues(selectedEntry.params, entryParams),
      ...buildParamValues(selectedExit.params, exitParams),
    ],
  };

  try {
    if (isEditing && editStrategy?.id) {
      await updateStrategy(editStrategy.id, request); 
    } else {
      await create(request);
    }

    if (onCreated) onCreated();
  } catch (err) {
    console.error("Fehler beim Speichern:", err);
  }
};

  if (loadingTypes) return <div>Lade Strategietypen...</div>;

  return (
    <div className="strategy-card">
      <form className="grid-container" onSubmit={handleSubmit}>
        <Typography variant="h6" className="strategy-title">
          {isEditing ? 'Strategie bearbeiten' : 'Neue Strategie erstellen'}
        </Typography>

        <TextField
          label="Strategiename"
          fullWidth
          required
          value={strategyName}
          onChange={(e) => setStrategyName(e.target.value)}
        />

        <Box display="flex" gap={2} mt={2}>
          <FormControl fullWidth required>
            <InputLabel>Entry-Strategietyp</InputLabel>
            <Select
              value={selectedEntry?.id || ""}
              onChange={(e) => {
                const id = Number(e.target.value);
                const found = data?.entryStrategies.find((s) => s.id === id) || null;
                setSelectedEntry(found);
                setEntryParams({});
              }}
              label="Entry-Strategietyp"
            >
              {data?.entryStrategies.map((s) => (
                <MenuItem key={s.id} value={s.id}>
                  {s.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl fullWidth required>
            <InputLabel>Exit-Strategietyp</InputLabel>
            <Select
              value={selectedExit?.id || ""}
              onChange={(e) => {
                const id = Number(e.target.value);
                const found = data?.exitStrategies.find((s) => s.id === id) || null;
                setSelectedExit(found);
                setExitParams({});
              }}
              label="Exit-Strategietyp"
            >
              {data?.exitStrategies.map((s) => (
                <MenuItem key={s.id} value={s.id}>
                  {s.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>

        {selectedEntry && (
          <Box mt={3}>
            <Typography variant="subtitle1">Entry-Parameter</Typography>
            <Box display="flex" gap={2} flexWrap="wrap" mt={1}>
              {selectedEntry.params.map((param) => (
                <TextField
                  key={param.id}
                  label={param.key}
                  type={param.inputType}
                  fullWidth
                  value={entryParams[param.key] || ""}
                  onChange={(e) => handleParamChange("entry", param.key, e.target.value)}
                />
              ))}
            </Box>
          </Box>
        )}

        {selectedExit && (
          <Box mt={3}>
            <Typography variant="subtitle1">Exit-Parameter</Typography>
            <Box display="flex" gap={2} flexWrap="wrap" mt={1}>
              {selectedExit.params.map((param) => (
                <TextField
                  key={param.id}
                  label={param.key}
                  type={param.inputType}
                  fullWidth
                  value={exitParams[param.key] || ""}
                  onChange={(e) => handleParamChange("exit", param.key, e.target.value)}
                />
              ))}
            </Box>
          </Box>
        )}

        <Box display="flex" gap={2} mt={4}>
          <Button 
            type="submit" 
            variant="contained" 
            color="primary" 
            fullWidth={!isEditing} 
            disabled={isSubmitting}
            className="save-btn"
          >
            {isSubmitting ? <CircularProgress size={24} /> : isEditing ? "Strategie updaten" : "Strategie speichern"}
          </Button>
          {isEditing && onCancel && (
            <Button 
              onClick={onCancel} 
              variant="outlined" 
              color="secondary" 
              fullWidth
              className="cancel-btn"
            >
              Abbrechen
            </Button>
          )}
        </Box>

        {error && (
          <Typography color="error" mt={2}>
            Fehler: {error}
          </Typography>
        )}
        {success && (
          <Typography color="green" mt={2}>
            Strategie erfolgreich gespeichert!
          </Typography>
        )}
      </form>
    </div>
  );
}
