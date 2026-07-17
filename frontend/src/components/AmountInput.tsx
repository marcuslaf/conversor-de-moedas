'use client';

import { useEffect, useState } from 'react';

interface AmountInputProps {
  value: number;
  onChange: (value: number) => void;
  label: string;
  id: string;
  disabled?: boolean;
}

export default function AmountInput({ value, onChange, label, id, disabled }: AmountInputProps) {
  const [displayValue, setDisplayValue] = useState(value.toString());

  useEffect(() => {
    setDisplayValue(value.toString());
  }, [value]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value;
    
    if (inputValue === '' || inputValue === '.') {
      setDisplayValue(inputValue);
      return;
    }

    const numericValue = parseFloat(inputValue);
    if (!isNaN(numericValue) && numericValue >= 0) {
      setDisplayValue(inputValue);
      onChange(numericValue);
    }
  };

  const handleBlur = () => {
    if (displayValue === '' || displayValue === '.') {
      setDisplayValue('0');
      onChange(0);
    }
  };

  return (
    <div className="w-full">
      <label htmlFor={id} className="block text-sm font-medium text-gray-700 mb-2">
        {label}
      </label>
      <input
        type="text"
        id={id}
        inputMode="decimal"
        value={displayValue}
        onChange={handleChange}
        onBlur={handleBlur}
        disabled={disabled}
        placeholder="0.00"
        className="w-full px-4 py-3 bg-white border border-gray-200 rounded-xl text-gray-900 text-lg font-semibold
                   focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent
                   transition-all duration-200
                   hover:border-gray-300
                   disabled:bg-gray-50 disabled:cursor-not-allowed
                   placeholder:text-gray-400"
      />
    </div>
  );
}
