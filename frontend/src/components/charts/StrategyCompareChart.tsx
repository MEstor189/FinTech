import React from "react";
import {
  BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer,
  ReferenceLine
} from "recharts";
import { calculateMaxDrawdown } from "../../utils/metrics";

type StrategyMetrics = {
  name: string;
  totalProfit: number;
  averageHoldingDays: number;
  tradeCount: number;
  profitPerTrade: number;
  maxDrawdown: number;
};

interface StrategyCompareChartProps {
  strategies: StrategyMetrics[];
  height?: number;
}

const metricsKeys = [
  { key: "totalProfit", label: "Profit" },
  { key: "profitPerTrade", label: "Profit/ Trade" },
  { key: "tradeCount", label: "Trades" },
  { key: "averageHoldingDays", label: "Avg. Hold" },
  { key: "maxDrawdown", label: "Drawdown" }
];

const colors = ["#5a6aff", "#ff4fd8"];

const StrategyCompareChart: React.FC<StrategyCompareChartProps> = ({ strategies, height = 400 }) => {
  const barData = metricsKeys.map(({ key, label }) => ({
    metric: label,
    ...Object.fromEntries(
      strategies.map((s) => [s.name, s[key as keyof StrategyMetrics]])
    )
  }));

  return (
    <ResponsiveContainer width="100%" height={height}>
      <BarChart data={barData} margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
        <CartesianGrid strokeDasharray="3 3" stroke="#2a2a3c" />
        <XAxis
          dataKey="metric"
          tick={{ fill: "#a259ff", fontWeight: 300, fontSize: 12, letterSpacing: 0 }}
        />
        <YAxis
          tick={{ fill: "#a259ff", fontWeight: 300, fontSize: 12, letterSpacing: 0 }}
        />
        <ReferenceLine y={0} stroke="#888" strokeDasharray="3 3" />
        <Tooltip
          formatter={(value: number, name: string) => [value.toFixed(2), name]}
          cursor={{ fill: 'transparent' }}
        />
        <Legend />
        {strategies.map((s, idx) => (
          <Bar
            key={s.name}
            dataKey={s.name}
            fill={colors[idx % colors.length]}
            radius={[4, 4, 0, 0]}
            activeBar={{
              fill: colors[idx % colors.length],
              stroke: '#fff',
              strokeWidth: 1
            }}
          />
        ))}
      </BarChart>
    </ResponsiveContainer>
  );
};

export default StrategyCompareChart;