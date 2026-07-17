'use client';

import { ConversionHistory } from '@/types';
import { getCurrencyByCode, formatCurrency } from '@/lib/currencies';

interface HistoryListProps {
  history: ConversionHistory[];
  onClear: () => void;
}

export default function HistoryList({ history, onClear }: HistoryListProps) {
  if (history.length === 0) {
    return null;
  }

  return (
    <div className="w-full">
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-lg font-semibold text-gray-900">Histórico</h3>
        <button
          onClick={onClear}
          className="text-sm text-gray-500 hover:text-gray-700 transition-colors"
        >
          Limpar
        </button>
      </div>

      <div className="space-y-3 max-h-64 overflow-y-auto">
        {history.map((item) => {
          const fromCurrency = getCurrencyByCode(item.request.from);
          const toCurrency = getCurrencyByCode(item.request.to);

          if (!fromCurrency || !toCurrency) return null;

          return (
            <div
              key={item.id}
              className="p-4 bg-white rounded-xl border border-gray-100 hover:border-gray-200 transition-colors"
            >
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-2">
                  <span className="text-lg">
                    {formatCurrency(item.request.amount, fromCurrency)}
                  </span>
                  <span className="text-gray-400">→</span>
                  <span className="text-lg font-semibold text-gray-900">
                    {formatCurrency(item.response.result, toCurrency)}
                  </span>
                </div>
                <span className="text-xs text-gray-400">
                  {item.timestamp.toLocaleTimeString('pt-BR', {
                    hour: '2-digit',
                    minute: '2-digit',
                  })}
                </span>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
