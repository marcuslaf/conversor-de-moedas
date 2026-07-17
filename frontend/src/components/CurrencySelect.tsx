'use client';

import { Currency, CurrencyCode } from '@/types';
import { currencies } from '@/lib/currencies';

interface CurrencySelectProps {
  value: CurrencyCode;
  onChange: (currency: CurrencyCode) => void;
  label: string;
  id: string;
}

export default function CurrencySelect({ value, onChange, label, id }: CurrencySelectProps) {
  return (
    <div className="w-full">
      <label htmlFor={id} className="block text-sm font-medium text-gray-700 mb-2">
        {label}
      </label>
      <select
        id={id}
        value={value}
        onChange={(e) => onChange(e.target.value as CurrencyCode)}
        className="w-full px-4 py-3 bg-white border border-gray-200 rounded-xl text-gray-900
                   focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent
                   transition-all duration-200 appearance-none cursor-pointer
                   hover:border-gray-300"
      >
        {currencies.map((currency) => (
          <option key={currency.code} value={currency.code}>
            {currency.flag} {currency.code} - {currency.name}
          </option>
        ))}
      </select>
    </div>
  );
}
