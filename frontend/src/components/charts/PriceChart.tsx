import React from "react";
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, Scatter,
  Brush
} from "recharts";
import './PriceChart.css';
import { Trade } from "../../types/simulation";
import CustomTooltip from "./CustomTooltip";

type PricePoint = {
  date: string;
  close: number;
};

type MarkerPoint = {
  date: string;
  price: number;
  type: "BUY" | "SELL";
  trade: Trade;
};

interface PriceChartProps {
  priceSeries: PricePoint[];
  markers: MarkerPoint[];
  height?: number;
}

const PriceChart: React.FC<PriceChartProps> = ({ priceSeries, markers, height = 400 }) => {

  const mergedData = priceSeries.map((p) => {
    const buy = markers.filter(t => t.date === p.date && t.type === "BUY");
    const sell = markers.filter(t => t.date === p.date && t.type === "SELL");

    return {
      ...p,
      buyMarkers: buy.map(b => b.trade),
      sellMarkers: sell.map((s => s.trade)),
    };
  });
  return (
    <ResponsiveContainer width="100%" height={height}>
      <LineChart data={mergedData}>
        <CartesianGrid strokeDasharray="3 3" stroke="#333" />

        <XAxis
          dataKey="date"
          tick={{ fill: "#a259ff", fontWeight: 300, fontSize: 12, letterSpacing: 0 }}
          axisLine={{ stroke: "#444" }}
          tickLine={{ stroke: "#444" }}
        />
        <YAxis
          tick={{ fill: "#a259ff", fontWeight: 300, fontSize: 12, letterSpacing: 0 }}
          axisLine={{ stroke: "#444" }}
          tickLine={{ stroke: "#444" }}
          domain={['dataMin - 10', 'dataMax + 10']}
        />
        <Tooltip content={<CustomTooltip />} />
        <Legend />
        <Line type="monotone" dataKey="close" stroke="#8884d8" name="Kurs" dot={false} />
        <Line
          type="monotone"
          dataKey="buyMarkers[0].entryPrice"
          stroke="green"
          name="BUY"
          dot={{ r: 5 }}
          activeDot={false}
          isAnimationActive={false}
        />
        <Line
          type="monotone"
          dataKey="sellMarkers[0].exitPrice"
          stroke="red"
          name="SELL"
          dot={{ r: 5 }}
          activeDot={false}
          isAnimationActive={false}
        />
        <Brush
          dataKey="date"
          height={30}
          stroke="#a259ff"
          travellerWidth={20}
          fill="#23243a"
        />
      </LineChart>
    </ResponsiveContainer>
  );
};

export default PriceChart;