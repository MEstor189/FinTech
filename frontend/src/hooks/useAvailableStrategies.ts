import { useEffect, useState } from "react";
import { fetchAllStrategies } from "../services/strategies";
import { StrategyConfigRequest } from "../types/strategies";

interface UseAvailableStrategiesResult {
  strategies: StrategyConfigRequest[];
  isLoading: boolean;
  error: string | null;
  refetch: () => void;
}

export function useAvailableStrategies(): UseAvailableStrategiesResult {
  const [strategies, setStrategies] = useState<StrategyConfigRequest[]>([]);
  const [isLoading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const load = async () => {
    try {
      const data = await fetchAllStrategies();
      setStrategies(data);
      setLoading(false);
    } catch (err: any) {
      setError(err.message || "Fehler beim Laden der Strategien");
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  return { strategies, isLoading, error, refetch: load };
}
