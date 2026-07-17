'use client';

import { useState } from 'react';
import { CurrencyCode } from '@/types';
import { useExchangeRate } from '@/hooks/useExchangeRate';
import CurrencySelect from '@/components/CurrencySelect';
import AmountInput from '@/components/AmountInput';
import ConvertButton from '@/components/ConvertButton';
import ConversionResult from '@/components/ConversionResult';
import SwapButton from '@/components/SwapButton';
import HistoryList from '@/components/HistoryList';
import ErrorMessage from '@/components/ErrorMessage';

export default function Home() {
  const [amount, setAmount] = useState<number>(1);
  const [fromCurrency, setFromCurrency] = useState<CurrencyCode>('USD');
  const [toCurrency, setToCurrency] = useState<CurrencyCode>('BRL');

  const { convert, isLoading, error, result, history, clearHistory, clearError } =
    useExchangeRate();

  const handleConvert = async () => {
    if (amount <= 0) return;
    
    await convert({
      amount,
      from: fromCurrency,
      to: toCurrency,
    });
  };

  const handleSwap = () => {
    setFromCurrency(toCurrency);
    setToCurrency(fromCurrency);
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && !isLoading) {
      handleConvert();
    }
  };

  return (
    <main className="min-h-screen py-8 px-4">
      <div className="max-w-md mx-auto">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">
            Conversor de Moedas
          </h1>
          <p className="text-gray-500">
            Taxas de câmbio em tempo real
          </p>
        </div>

        <div className="space-y-6" onKeyPress={handleKeyPress}>
          <AmountInput
            id="amount"
            label="Valor"
            value={amount}
            onChange={setAmount}
            disabled={isLoading}
          />

          <div className="space-y-4">
            <CurrencySelect
              id="from-currency"
              label="De:"
              value={fromCurrency}
              onChange={setFromCurrency}
            />

            <div className="flex justify-center">
              <SwapButton onClick={handleSwap} disabled={isLoading} />
            </div>

            <CurrencySelect
              id="to-currency"
              label="Para:"
              value={toCurrency}
              onChange={setToCurrency}
            />
          </div>

          <ConvertButton
            onClick={handleConvert}
            isLoading={isLoading}
            disabled={amount <= 0}
          />

          {error && (
            <ErrorMessage message={error} onDismiss={clearError} />
          )}

          <ConversionResult result={result} />

          <HistoryList history={history} onClear={clearHistory} />
        </div>

        <footer className="mt-8 text-center text-sm text-gray-400">
          <p>
            Dados fornecidos por{' '}
            <a
              href="https://www.exchangerate-api.com/"
              target="_blank"
              rel="noopener noreferrer"
              className="text-primary-500 hover:text-primary-600"
            >
              ExchangeRate-API
            </a>
          </p>
        </footer>
      </div>
    </main>
  );
}
