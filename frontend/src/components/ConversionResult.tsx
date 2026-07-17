'use client';

import { ConversionResponse } from '@/types';
import { getCurrencyByCode, formatCurrency, formatRate } from '@/lib/currencies';

interface ConversionResultProps {
  result: ConversionResponse | null;
}

export default function ConversionResult({ result }: ConversionResultProps) {
  if (!result) {
    return (
      <div className="w-full p-6 bg-gray-50 rounded-2xl border border-gray-100">
        <p className="text-center text-gray-500">
          Insira um valor e clique em Converter
        </p>
      </div>
    );
  }

  const fromCurrency = getCurrencyByCode(result.from);
  const toCurrency = getCurrencyByCode(result.to);

  if (!fromCurrency || !toCurrency) {
    return null;
  }

  return (
    <div className="w-full p-6 bg-gradient-to-br from-success-50 to-white rounded-2xl border-2 border-success-500/20">
      <div className="text-center">
        <p className="text-sm text-gray-500 mb-2">Resultado</p>
        
        <div className="mb-4">
          <span className="text-3xl font-bold text-gray-900">
            {formatCurrency(result.result, toCurrency)}
          </span>
        </div>

        <div className="flex items-center justify-center gap-2 text-gray-600 mb-4">
          <span className="text-lg">
            {formatCurrency(result.amount, fromCurrency)}
          </span>
          <span className="text-gray-400">=</span>
          <span className="text-lg font-semibold text-gray-900">
            {formatCurrency(result.result, toCurrency)}
          </span>
        </div>

        <div className="pt-4 border-t border-gray-200">
          <p className="text-sm text-gray-500">
            1 {result.from} = {formatRate(result.exchangeRate)} {result.to}
          </p>
        </div>
      </div>
    </div>
  );
}
