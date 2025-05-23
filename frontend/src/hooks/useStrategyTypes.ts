import { useEffect, useState } from "react";
import { fetchStrategyTypes } from "../services/strategies";
import { StrategyTypes } from "../types/strategies";

interface UseStrategyTypesResult {
  data: StrategyTypes | null;
  isLoading: boolean;
  error: string | null;
}

export function useStrategyTypes(): UseStrategyTypesResult {
  const [data, setData] = useState<StrategyTypes | null>(null);
  const [isLoading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadTypes = async () => {
      try {
        const result = await fetchStrategyTypes();
        setData(result);
        setLoading(false);
      } catch (err: any) {
        setError(err.message || "Fehler beim Laden der Strategie-Typen");
        setLoading(false);
      }
    };

    loadTypes();
  }, []);

  return { data, isLoading, error };
}
