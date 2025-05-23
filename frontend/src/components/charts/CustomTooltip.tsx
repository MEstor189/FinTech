import React from "react";
import { Trade } from "../../types/simulation";
import './CustomTooltip.css';

interface CustomTooltipProps {
    active?: boolean;
    payload?: any[];
    label?: string;
}

const CustomTooltip: React.FC<CustomTooltipProps> = ({ active, payload, label }) => {
    if (!active || !payload || !payload.length) return null;

    const close = payload.find(p => p.dataKey === "close")?.value;
    const buyAll = payload.find(p => p.dataKey === "buyMarkers[0].entryPrice")?.payload?.buyMarkers as Trade[] || [];
    const sellAll = payload.find(p => p.dataKey === "sellMarkers[0].exitPrice")?.payload?.sellMarkers as Trade[] || [];


    return (
        <div className="custom-tooltip">
            <p><strong>📅 Datum:</strong> {label}</p>
            <p><strong>💰 Close:</strong> {close?.toFixed(2)}</p>

            {buyAll.length > 0 && (
                <>
                    <p className="tooltip-header" style={{ color: 'lime' }}>🟢 Käufe:</p>
                    {buyAll.map((b, i) => (
                        <p key={i} className="tooltip-line">
                            • {b.entryPrice.toFixed(2)} am {b.entryDate}
                        </p>
                    ))}
                </>
            )}

            {sellAll.length > 0 && (
                <>
                    <p className="tooltip-header" style={{ color: 'red' }}>🔴 Verkäufe:</p>
                    {sellAll.map((s, i) => (
                        <p key={i} className="tooltip-line" style={{ color: s.profitPercent >= 0 ? 'lime' : 'red' }}>
                            • {s.exitPrice.toFixed(2)} | {s.profitPercent.toFixed(2)}% | von {s.entryDate}
                        </p>
                    ))}
                </>
            )}

        </div>
    );
};

export default CustomTooltip;
