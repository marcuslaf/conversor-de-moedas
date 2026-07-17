import { ConversionRequest, ConversionResponse, Currency, ExchangeRate, ApiError } from '@/types';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

async function fetchApi<T>(endpoint: string, options?: RequestInit): Promise<T> {
  const url = `${API_BASE_URL}${endpoint}`;
  
  const response = await fetch(url, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...options?.headers,
    },
  });

  if (!response.ok) {
    const error: ApiError = await response.json();
    throw new Error(error.message || `Erro ${response.status}`);
  }

  return response.json();
}

export async function convertCurrency(request: ConversionRequest): Promise<ConversionResponse> {
  return fetchApi<ConversionResponse>('/api/convert', {
    method: 'POST',
    body: JSON.stringify(request),
  });
}

export async function getCurrencies(): Promise<Currency[]> {
  const data = await fetchApi<{ code: string; name: string; symbol: string }[]>('/api/currencies');
  return data.map((c) => ({
    ...c,
    code: c.code as Currency['code'],
    flag: getFlagEmoji(c.code),
  }));
}

export async function getExchangeRate(from: string, to: string): Promise<ExchangeRate> {
  const data = await fetchApi<{ from: string; to: string; rate: number }>(
    `/api/rate?from=${from}&to=${to}`
  );
  return {
    from: data.from as ExchangeRate['from'],
    to: data.to as ExchangeRate['to'],
    rate: data.rate,
  };
}

export async function healthCheck(): Promise<boolean> {
  try {
    await fetchApi<{ status: string }>('/api/health');
    return true;
  } catch {
    return false;
  }
}

function getFlagEmoji(countryCode: string): string {
  const codePoints = countryCode
    .toUpperCase()
    .split('')
    .map((char) => 127397 + char.charCodeAt(0));
  return String.fromCodePoint(...codePoints);
}
