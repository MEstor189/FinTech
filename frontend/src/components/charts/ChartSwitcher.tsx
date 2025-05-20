import React from "react";
import "./ChartSwitcher.css";

export type ChartType = "price" | "equity" | "compare";

interface ChartSwitcherProps {
  selectedChart: ChartType;
  onChange: (type: ChartType) => void;
}

const ChartSwitcher: React.FC<ChartSwitcherProps> = ({ selectedChart, onChange }) => {
  const buttons: { label: string; type: ChartType }[] = [
    { label: "Kursverlauf", type: "price" },
    { label: "Equity-Kurve", type: "equity" },
    { label: "Strategie-Vergleich", type: "compare" },
  ];

  return (
    <div style={{ display: "flex", gap: "1rem", marginBottom: "1rem" }}>
      {buttons.map(({ label, type }) => (
        <button
          key={type}
          onClick={() => onChange(type)}
          className={`chart-switcher-btn${selectedChart === type ? ' active' : ''}`}
        >
          {label}
        </button>
      ))}
    </div>
  );
};

export default ChartSwitcher;