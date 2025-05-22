
import { API_BASE_URL } from "./config";

export async function apiFetch<T = any>(
  endpoint: string,
  options?: RequestInit
): Promise<T> {
  const res = await fetch(`${API_BASE_URL}${endpoint}`, {
    headers: {
      "Content-Type": "application/json",
    },
    ...options,
  });

  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(`API Error: ${res.status} - ${errorText}`);
  }

  return res.json();
}
