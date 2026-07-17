import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: 'Conversor de Moedas',
  description: 'Converta moedas com taxas de câmbio em tempo real',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="pt-BR">
      <body>{children}</body>
    </html>
  );
}
