import React from "react";
import {
  RadarChart, PolarGrid, PolarAngleAxis, PolarRadiusAxis, Radar, Legend, ResponsiveContainer
} from "recharts";

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
}

const metricsKeys = [
  { key: "totalProfit", label: "Profit" },
  { key: "averageHoldingDays", label: "Avg. Hold" },
  { key: "tradeCount", label: "Trades" },
  { key: "winRate", label: "Win %" },
  { key: "maxDrawdown", label: "Drawdown" }
];

const StrategyCompareChart: React.FC<StrategyCompareChartProps> = ({ strategies }) => {
  const radarData = metricsKeys.map(({ key, label }) => {
    const entry: any = { metric: label };
    strategies.forEach((s) => {
      entry[s.name] = s[key as keyof StrategyMetrics];
    });
    return entry;
  });

  const colors = ["#8884d8", "#82ca9d", "#ffc658", "#ff7300", "#00c49f"];

  return (
    <ResponsiveContainer width="100%" height={400}>
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
            fillOpacity={0.6}
          />
        ))}
        <Legend />
      </RadarChart>
    </ResponsiveContainer>
  );
};

export default StrategyCompareChart;