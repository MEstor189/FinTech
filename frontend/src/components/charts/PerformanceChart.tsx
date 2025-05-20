import React from "react";
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer
} from "recharts";
import './PerformanceChart.css';

type PerformancePoint = {
  date: string;
  performance: number;
};

type StrategyPerformance = {
  name: string;
  data: PerformancePoint[];
};

interface PerformanceChartProps {
  strategies: StrategyPerformance[];
  height?: number;
}

const colors = ["#8884d8", "#82ca9d", "#ffc658", "#ff7300", "#00c49f"];

const PerformanceChart: React.FC<PerformanceChartProps> = ({ strategies, height = 400 }) => {
  const mergedData: { date: string; [strategyName: string]: number | string }[] =
    strategies[0]?.data.map((_, idx) => {
      const date = strategies[0].data[idx].date;
      const point: any = { date };
      strategies.forEach((s) => {
        point[s.name] = s.data[idx]?.performance ?? null;
      });
      return point;
    }) || [];

  return (
    <div className="performance-chart-container" style={{ height }}>
      <ResponsiveContainer width="100%" height="100%">
        <LineChart data={mergedData}>
          <CartesianGrid strokeDasharray="3 3" stroke="var(--border-color)" />
          <XAxis 
            dataKey="date" 
            stroke="var(--text-color)"
            tick={{ fill: 'var(--text-color)' }}
          />
          <YAxis
            tickFormatter={(val) => `${val}%`}
            domain={["auto", "auto"]}
            label={{ 
              value: "Performance (%)", 
              angle: -90, 
              position: "insideLeft",
              fill: 'var(--text-color)'
            }}
            stroke="var(--text-color)"
            tick={{ fill: 'var(--text-color)' }}
          />
          <Tooltip 
            formatter={(value: number) => [`${value.toFixed(2)}%`, 'Performance']}
            contentStyle={{
              backgroundColor: 'var(--background-color)',
              border: '1px solid var(--border-color)',
              borderRadius: '4px',
              color: 'var(--text-color)'
            }}
          />
          <Legend />
          {strategies.map((s, idx) => (
            <Line
              key={s.name}
              type="monotone"
              dataKey={s.name}
              stroke={colors[idx % colors.length]}
              dot={false}
              name={s.name}
              strokeWidth={2}
            />
          ))}
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default PerformanceChart; 