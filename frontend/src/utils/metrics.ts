export function calculateMaxDrawdown(
  performanceData: { date: string; performance: number }[]
): number {
  if (!performanceData || performanceData.length === 0) return 0;

  let maxPeak = performanceData[0].performance;
  let maxDrawdown = 0;

  for (const point of performanceData) {
    const current = point.performance;
    if (current > maxPeak) {
      maxPeak = current;
    } else {
      const drawdown = ((current - maxPeak) / maxPeak) * 100;
      if (drawdown < maxDrawdown) {
        maxDrawdown = drawdown;
      }
    }
  }

  return Math.abs(maxDrawdown);
}