import React from "react";

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
          style={{
            padding: "0.5rem 1rem",
            backgroundColor: selectedChart === type ? "#007bff" : "#e0e0e0",
            color: selectedChart === type ? "#fff" : "#000",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer",
          }}
        >
          {label}
        </button>
      ))}
    </div>
  );
};

export default ChartSwitcher;