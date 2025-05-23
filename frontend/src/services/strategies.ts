import { apiFetch } from "./api";
import {
  StrategyConfigRequest,
  StrategyTypes,
  CreateStrategyRequest,
} from "../types/strategies";

export async function fetchAllStrategies(): Promise<StrategyConfigRequest[]> {
  return await apiFetch("/api/strategies/getAll");
}

export async function fetchStrategyTypes(): Promise<StrategyTypes> {
  return await apiFetch("/api/strategies/getTypes");
}

export async function createStrategy(data: CreateStrategyRequest): Promise<StrategyConfigRequest> {
  return await apiFetch("/api/strategies/create", {
    method: "POST",
    body: JSON.stringify(data),
  });
}

export async function deleteStrategy(strategyName: string): Promise<void> {
  return await apiFetch(`/api/strategies/delete/${strategyName}`, {
    method: "DELETE",
  });
}