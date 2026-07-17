'use client';

import { useState, useCallback } from 'react';
import { ConversionRequest, ConversionResponse, ConversionHistory } from '@/types';
import { convertCurrency } from '@/lib/api';

interface UseExchangeRateReturn {
  convert: (request: ConversionRequest) => Promise<ConversionResponse | null>;
  isLoading: boolean;
  error: string | null;
  result: ConversionResponse | null;
  history: ConversionHistory[];
  clearHistory: () => void;
  clearError: () => void;
}

export function useExchangeRate(): UseExchangeRateReturn {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [result, setResult] = useState<ConversionResponse | null>(null);
  const [history, setHistory] = useState<ConversionHistory[]>([]);

  const convert = useCallback(async (request: ConversionRequest): Promise<ConversionResponse | null> => {
    setIsLoading(true);
    setError(null);

    try {
      const response = await convertCurrency(request);
      setResult(response);

      const historyItem: ConversionHistory = {
        id: `${Date.now()}-${Math.random().toString(36).substr(2, 9)}`,
        request,
        response,
        timestamp: new Date(),
      };
      setHistory((prev) => [historyItem, ...prev].slice(0, 20));

      return response;
    } catch (err) {
      const message = err instanceof Error ? err.message : 'Erro desconhecido';
      setError(message);
      return null;
    } finally {
      setIsLoading(false);
    }
  }, []);

  const clearHistory = useCallback(() => {
    setHistory([]);
  }, []);

  const clearError = useCallback(() => {
    setError(null);
  }, []);

  return {
    convert,
    isLoading,
    error,
    result,
    history,
    clearHistory,
    clearError,
  };
}
