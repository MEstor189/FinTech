// Request-Typ
export interface SimulationRequest {
  symbol: string;
  startDate: string;
  endDate: string;
  investmentPerTrade: number;
  requestStrategies: StrategyRequest[];
}

export interface StrategyRequest {
  strategyName: string;
  entryStrategyType: string;
  exitStrategyType: string;
  entryParams: Record<string, any>;
  exitParams: Record<string, any>;
}

// Response-Typen
export interface SimulationResponse {
  symbol: string;
  startDate: string;
  endDate: string;
  investmentPerTrade: number;
  stockData: StockDay[];
  strategies: StrategyResult[];
}

export interface StockDay {
  symbol: string;
  date: string;
  openPrice: number;
  closePrice: number;
  highPrice: number;
  lowPrice: number;
  volumen: number;
}

export interface StrategyResult {
  strategyName: string;
  entryStrategyType: string;
  exitStrategyType: string;
  entryParams: Record<string, any>;
  exitParams: Record<string, any>;
  tradeCount: number;
  totalProfit: number;
  averageHoldingDays: number;
  trades: Trade[];
}

export interface Trade {
  symbol: string;
  entryPrice: number;
  exitPrice: number;
  quantity: number;
  entryDate: string;
  exitDate: string;
  strategyName: string;
  profitAbsolute: number;
  profitPercent: number;
  holdingDays: number;
}
