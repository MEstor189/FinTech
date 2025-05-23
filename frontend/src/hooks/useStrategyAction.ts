export const deleteStrategy = async (id: number): Promise<void> => {
  const response = await fetch(`/api/strategies/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) {
    throw new Error(`Strategie konnte nicht gelöscht werden (ID ${id})`);
  }
};

export const updateStrategy = async (
  id: number,
  body: any // später: StrategyUpdateRequest
): Promise<void> => {
  const response = await fetch(`/api/strategies/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body),
  });
  if (!response.ok) {
    throw new Error(`Strategie konnte nicht aktualisiert werden (ID ${id})`);
  }
}