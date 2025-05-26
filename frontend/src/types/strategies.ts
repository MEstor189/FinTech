export interface ParamKey {
  id: number;
  key: string;
  type: string; 
  inputType: "number" /* | "number" | "checkbox" */; 
  description?: string;
}

// Ein Strategie-Typ (z. B. "BUY_THE_DIP") mit zugehörigen Parametern
export interface StrategyTypeWithParams {
  id: number;
  name: string;
  description?: string;
  params: ParamKey[];
}

// Antwort von `/getTypes`
export interface StrategyTypes {
  entryStrategies: StrategyTypeWithParams[];
  exitStrategies: StrategyTypeWithParams[];
}

// Anfrage an /create
export interface CreateStrategyRequest {
  name: string;
  entryStrategyTypeId: number;
  exitStrategyTypeId: number;
  paramValues: {
    paramKeyId: number;
    value: string | number;
  }[];
}
// Einzelne gespeicherte Strategie
export interface StrategyConfigRequest {
  id: number;
  strategyName: string;
  entryStrategyName: string;
  exitStrategyName: string;
  entryParams: { key: string; value: string; type: string; inputType: string }[];
  exitParams: { key: string; value: string; type: string; inputType: string }[];
}