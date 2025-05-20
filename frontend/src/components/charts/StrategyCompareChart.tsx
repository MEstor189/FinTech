import React from "react";
import {
  RadarChart, PolarGrid, PolarAngleAxis, PolarRadiusAxis, Radar, Legend, ResponsiveContainer
} from "recharts";
import './StrategyCompareChart.css';

type StrategyMetrics = {
  name: string;
  totalProfit: number;
  averageHoldingDays: number;
  tradeCount: number;
  winRate: number;
  maxDrawdown: number;
};

interface StrategyCompareChartProps {
  strategies: StrategyMetrics[];
  height?: number;
}

const metricsKeys = [
  { key: "totalProfit", label: "Profit" },
  { key: "averageHoldingDays", label: "Avg. Hold" },
  { key: "tradeCount", label: "Trades" },
  { key: "winRate", label: "Win %" },
  { key: "maxDrawdown", label: "Drawdown" }
];

const StrategyCompareChart: React.FC<StrategyCompareChartProps> = ({ strategies, height = 400 }) => {
  const radarData = metricsKeys.map(({ key, label }) => {
    const entry: any = { metric: label };
    strategies.forEach((s) => {
      entry[s.name] = s[key as keyof StrategyMetrics];
    });
    return entry;
  });

  const colors = ["#5a6aff", "#ff4fd8"];

  return (
    <ResponsiveContainer width="100%" height={height}>
      <RadarChart outerRadius={150} data={radarData}>
        <PolarGrid />
        <PolarAngleAxis dataKey="metric" />
        <PolarRadiusAxis />
        {strategies.map((s, idx) => (
          <Radar
            key={s.name}
            name={s.name}
            dataKey={s.name}
            stroke={colors[idx % colors.length]}
            fill={colors[idx % colors.length]}
            fillOpacity={0.4}
          />
        ))}
        <Legend />
      </RadarChart>
    </ResponsiveContainer>
  );
};

export default StrategyCompareChart;