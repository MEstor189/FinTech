import { Box, Typography } from '@mui/material';
import { useStrategyTypes } from '../../hooks/useStrategyTypes';
import StrategyTypeInfo from './StrategyTypeInfo';
import './StrategyTypeInfoList.css';

interface StrategyTypeInfoListProps {
    direction: 'entry' | 'exit';
}

export default function StrategyTypeInfoList({ direction }: StrategyTypeInfoListProps) {
    const { data, isLoading } = useStrategyTypes();

    const strategies = direction === 'entry' ? data?.entryStrategies : data?.exitStrategies;

    if (isLoading) return <Typography>Lade Strategietypen...</Typography>;

    return (
        <Box className="strategy-info-wrapper">
            <Typography className="strategy-info-title">
                {direction === 'entry' ? 'Entry-Strategien' : 'Exit-Strategien'}
            </Typography>
            {strategies?.map((s) => (
                <Box className="strategy-info-box" key={s.id}>
                    <StrategyTypeInfo strategy={s} />
                </Box>
            ))}
        </Box>
    );
}