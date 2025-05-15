import * as React from 'react';
import { LineChart } from '@mui/x-charts/LineChart';
import { useEffect, useState } from 'react';
import { getData } from '../api/dataApi';


function getFinData(){
    const [pricelist, setPricelist] = useState<number[]>([]);

    useEffect(() => {
      // Aufruf der Funktion zum Abrufen der Daten
      const fetchData = async () => {
        const data = await getData();
        setPricelist(data);
      };
  
      fetchData();
    }, []);

    return (
        <LineChart
        width={500}
        height={300}
        series={[{ data: pricelist, label: 'uv', area: true, showMark: false }]} // Dataset wird hier verwendet
        //xAxis={[{ scaleType: 'point', data: xLabels }]} // Die x-Achse wird weiterhin die Labels verwenden
      />



    );
  }
export default getFinData;