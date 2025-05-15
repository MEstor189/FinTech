const API_URL = 'http://localhost:8080/data';

export const getData = async () => {
  const response = await fetch(`${API_URL}`);
  console.log("t");
  
  if (!response.ok) {
    // Fehlerbehandlung, falls die Antwort nicht erfolgreich ist
    throw new Error('Network response was not ok');
  }
  
  const pricelist: number[] = await response.json();
  return pricelist;
};
