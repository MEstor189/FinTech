// src/services/simulation.ts
import { apiFetch } from "./api";
import { SimulationRequest, SimulationResponse } from "../types/simulation";

export async function fetchSimulation(
  body: SimulationRequest
): Promise<SimulationResponse> {
  console.log(body)
  return await apiFetch<SimulationResponse>("/api/simulation", {
    method: "POST",
    body: JSON.stringify(body),
  });
}
