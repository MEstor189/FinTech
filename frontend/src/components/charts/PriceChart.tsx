import React from "react";
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, Scatter
} from "recharts";
import './PriceChart.css';

type PricePoint = {
  date: string;
  close: number;
};

type Trade = {
  date: string;
  type: "BUY" | "SELL";
  price: number;
};

type MovingAverage = { date: string; value: number };

interface PriceChartProps {
  priceSeries: PricePoint[];
  trades: Trade[];
  movingAverages?: {
    short?: MovingAverage[];
    long?: MovingAverage[];
  };
  height?: number;
}

const PriceChart: React.FC<PriceChartProps> = ({ priceSeries, trades, movingAverages, height = 400 }) => {
  const mergedData = priceSeries.map((p) => ({
    ...p,
    shortMA: movingAverages?.short?.find((ma) => ma.date === p.date)?.value,
    longMA: movingAverages?.long?.find((ma) => ma.date === p.date)?.value,
  }));

  const buyPoints = trades.filter((t) => t.type === "BUY");
  const sellPoints = trades.filter((t) => t.type === "SELL");

  return (
    <ResponsiveContainer width="100%" height={height}>
      <LineChart data={mergedData}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="date" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="close" stroke="#8884d8" name="Kurs" dot={false} />
        {movingAverages?.short && (
          <Line type="monotone" dataKey="shortMA" stroke="#00c49f" name="Short MA" dot={false} />
        )}
        {movingAverages?.long && (
          <Line type="monotone" dataKey="longMA" stroke="#ff7300" name="Long MA" dot={false} />
        )}
        <Scatter data={buyPoints} fill="green" shape="circle" name="Buy" />
        <Scatter data={sellPoints} fill="red" shape="circle" name="Sell" />
      </LineChart>
    </ResponsiveContainer>
  );
};

export default PriceChart;