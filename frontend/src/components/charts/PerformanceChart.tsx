import React from "react";
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer,
  ReferenceLine
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
  const mergedData: { date: string;[strategyName: string]: number | string }[] =
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
          <CartesianGrid strokeDasharray="3 3" stroke="#333" />
          <XAxis
            dataKey="date"
            stroke="#444"
            tick={{ fill: "#a259ff", fontWeight: 300, fontSize: 12, letterSpacing: 0 }}
          />
          <YAxis
            tickFormatter={(val) => `${val}%`}
            domain={["auto", "auto"]}
            label={{
              value: "Performance (%)",
              angle: -90,
              position: "insideLeft",
              fill: '#a259ff'
            }}
            stroke="#444"
            tick={{ fill: "#a259ff", fontWeight: 300, fontSize: 12, letterSpacing: 0 }}
          />
          <Tooltip
            formatter={(value: number) => [`${value.toFixed(2)}%`, 'Performance']}
          />
          <Legend />
          <ReferenceLine y={0} stroke="#888" strokeDasharray="3 3" />
          {strategies.map((s, idx) => (
            <Line
              key={s.name}
              type="monotone"
              dataKey={s.name}
              stroke={colors[idx % colors.length]}
              dot={false}
              name={s.name}
              strokeWidth={2}

              label={{
                position: "right",
                fill: colors[idx % colors.length],
                formatter: (val: number) => `${val.toFixed(1)}%`,
                fontSize: 12,
                fontWeight: 600
              }}
            />
          ))}
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default PerformanceChart; 