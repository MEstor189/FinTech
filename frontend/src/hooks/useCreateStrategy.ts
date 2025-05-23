import { useState } from "react";
import { createStrategy } from "../services/strategies";
import { CreateStrategyRequest, StrategyConfigRequest } from "../types/strategies";

interface UseCreateStrategyResult {
  create: (data: CreateStrategyRequest) => Promise<void>;
  isLoading: boolean;
  error: string | null;
  success: boolean;
}

export function useCreateStrategy(): UseCreateStrategyResult {
  const [isLoading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);

  const create = async (data: CreateStrategyRequest) => {
    setLoading(true);
    setError(null);
    setSuccess(false);

    try {
      await createStrategy(data);
      setSuccess(true);
    } catch (err: any) {
      setError(err.message || "Fehler beim Erstellen der Strategie");
    } finally {
      setLoading(false);
    }
  };

  return { create, isLoading, error, success };
}