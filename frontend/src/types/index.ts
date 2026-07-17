export type CurrencyCode = 
  | 'USD' 
  | 'BRL' 
  | 'EUR' 
  | 'GBP' 
  | 'JPY' 
  | 'CAD' 
  | 'AUD' 
  | 'CHF' 
  | 'CNY' 
  | 'INR';

export interface Currency {
  code: CurrencyCode;
  name: string;
  symbol: string;
  flag: string;
}

export interface ConversionRequest {
  amount: number;
  from: CurrencyCode;
  to: CurrencyCode;
}

export interface ConversionResponse {
  amount: number;
  from: CurrencyCode;
  to: CurrencyCode;
  exchangeRate: number;
  result: number;
  timestamp: string;
}

export interface ExchangeRate {
  from: CurrencyCode;
  to: CurrencyCode;
  rate: number;
}

export interface ApiError {
  status: number;
  error: string;
  message: string;
  timestamp: string;
}

export interface ConversionHistory {
  id: string;
  request: ConversionRequest;
  response: ConversionResponse;
  timestamp: Date;
}
