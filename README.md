# Conversor de Moedas

Conversor de moedas com taxas de câmbio em tempo real, desenvolvido com React e Next.js.

![Next.js](https://img.shields.io/badge/Next.js-14-black?style=for-the-badge&logo=next.js)
![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![TypeScript](https://img.shields.io/badge/TypeScript-5.3-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind-3.4-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)

## Funcionalidades

- Conversão entre 10 moedas diferentes
- Taxas de câmbio em tempo real via ExchangeRate-API
- Interface responsiva e moderna
- Troca rápida de moedas
- Validação de entrada em tempo real
- Histórico de conversões
- Cache de taxas por 30 minutos

## Moedas Disponíveis

| Código | Moeda |
|--------|-------|
| USD | Dólar Americano |
| BRL | Real Brasileiro |
| EUR | Euro |
| GBP | Libra Esterlina |
| JPY | Iene Japonês |
| CAD | Dólar Canadense |
| AUD | Dólar Australiano |
| CHF | Franco Suíço |
| CNY | Yuan Chinês |
| INR | Rúpia Indiana |

## Pré-requisitos

- [Node.js 18+](https://nodejs.org/)
- [API Key da ExchangeRate-API](https://www.exchangerate-api.com/)

## Instalação

```bash
# Clone o repositório
git clone https://github.com/marcuslaf/conversor-de-moedas.git
cd conversor-de-moedas/frontend

# Instale as dependências
npm install

# Configure a API key
cp .env.example .env.local
# Edite .env.local com sua chave

# Execute o projeto
npm run dev
```

## Configuração da API

1. Acesse [ExchangeRate-API](https://www.exchangerate-api.com/)
2. Crie uma conta gratuita
3. Copie sua API key
4. Crie o arquivo `.env.local` na pasta `frontend/`:

```env
EXCHANGE_RATE_API_KEY=sua_chave_aqui
```

## Deploy na Vercel

1. Faça push do código para o GitHub
2. Acesse [vercel.com](https://vercel.com)
3. Importe o repositório
4. Configure a variável de ambiente `EXCHANGE_RATE_API_KEY`
5. Deploy automático

Ou via CLI:

```bash
npm i -g vercel
vercel login
vercel
```

## Estrutura do Projeto

```
frontend/
├── src/
│   ├── app/
│   │   ├── api/
│   │   │   └── convert/
│   │   │       └── route.ts    # API proxy
│   │   ├── layout.tsx          # Layout principal
│   │   └── page.tsx            # Página principal
│   ├── components/
│   │   ├── CurrencySelect.tsx  # Seletor de moedas
│   │   ├── AmountInput.tsx     # Campo de valor
│   │   ├── ConvertButton.tsx   # Botão de conversão
│   │   └── ConversionResult.tsx # Exibição do resultado
│   ├── hooks/
│   │   └── useExchangeRate.ts  # Hook de conversão
│   └── lib/
│       └── currencies.ts       # Lista de moedas
├── public/
├── .env.example
├── next.config.js
├── tailwind.config.ts
└── package.json
```

## Tecnologias

- **Next.js 14** - Framework React com App Router
- **React 18** - Biblioteca de interfaces
- **TypeScript** - Tipagem estática
- **Tailwind CSS** - Estilização utility-first
- **ExchangeRate-API** - API de taxas de câmbio

## License

MIT
