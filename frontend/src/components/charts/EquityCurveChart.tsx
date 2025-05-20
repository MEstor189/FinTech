import React from "react";
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer
} from "recharts";

type EquityPoint = {
  date: string;
  value: number;
};

type StrategyEquity = {
  name: string;
  equityCurve: EquityPoint[];
};

interface EquityCurveChartProps {
  strategies: StrategyEquity[];
}

const colors = ["#8884d8", "#82ca9d", "#ffc658", "#ff7300", "#00c49f"];

const EquityCurveChart: React.FC<EquityCurveChartProps> = ({ strategies }) => {
  // Nehme an, dass alle Strategien die gleiche Zeitreihe nutzen
  const mergedData = strategies[0].equityCurve.map((_, idx) => {
    const entry: any = { date: strategies[0].equityCurve[idx].date };
    strategies.forEach((s, i) => {
      entry[s.name] = s.equityCurve[idx].value;
    });
    return entry;
  });

  return (
    <ResponsiveContainer width="100%" height={400}>
      <LineChart data={mergedData}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="date" />
        <YAxis />
        <Tooltip />
        <Legend />
        {strategies.map((s, idx) => (
          <Line
            key={s.name}
            type="monotone"
            dataKey={s.name}
            stroke={colors[idx % colors.length]}
            dot={false}
            name={s.name}
          />
        ))}
      </LineChart>
    </ResponsiveContainer>
  );
};

export default EquityCurveChart;
