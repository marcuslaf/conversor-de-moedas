import { Currency, CurrencyCode } from '@/types';

export const currencies: Currency[] = [
  { code: 'USD', name: 'Dólar Americano', symbol: '$', flag: '🇺🇸' },
  { code: 'BRL', name: 'Real Brasileiro', symbol: 'R$', flag: '🇧🇷' },
  { code: 'EUR', name: 'Euro', symbol: '€', flag: '🇪🇺' },
  { code: 'GBP', name: 'Libra Esterlina', symbol: '£', flag: '🇬🇧' },
  { code: 'JPY', name: 'Iene Japonês', symbol: '¥', flag: '🇯🇵' },
  { code: 'CAD', name: 'Dólar Canadense', symbol: 'C$', flag: '🇨🇦' },
  { code: 'AUD', name: 'Dólar Australiano', symbol: 'A$', flag: '🇦🇺' },
  { code: 'CHF', name: 'Franco Suíço', symbol: 'Fr', flag: '🇨🇭' },
  { code: 'CNY', name: 'Yuan Chinês', symbol: '¥', flag: '🇨🇳' },
  { code: 'INR', name: 'Rúpia Indiana', symbol: '₹', flag: '🇮🇳' },
];

export function getCurrencyByCode(code: CurrencyCode): Currency | undefined {
  return currencies.find((c) => c.code === code);
}

export function formatCurrency(amount: number, currency: Currency): string {
  return `${currency.symbol} ${amount.toLocaleString('pt-BR', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  })}`;
}

export function formatRate(rate: number): string {
  return rate.toLocaleString('pt-BR', {
    minimumFractionDigits: 4,
    maximumFractionDigits: 4,
  });
}
