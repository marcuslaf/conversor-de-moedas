# Conversor de Moedas

Conversor de moedas com taxas de câmbio em tempo real, com frontend React/Next.js e backend Java/Spring Boot.

![Next.js](https://img.shields.io/badge/Next.js-14-black?style=for-the-badge&logo=next.js)
![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.3-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind-3.4-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white)

## Funcionalidades

- Conversão entre 10 moedas diferentes
- Taxas de câmbio em tempo real via ExchangeRate-API
- Interface responsiva e moderna (React)
- API REST documentada (Spring Boot)
- Cache de taxas por 30 minutos
- Validação de entrada em tempo real
- Histórico de conversões
- Deploy automático na Vercel

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

## Estrutura do Projeto

```
conversor-de-moedas/
├── frontend/               # Frontend React/Next.js
│   ├── src/
│   │   ├── app/           # App Router
│   │   ├── components/    # Componentes React
│   │   └── lib/           # Utilitários
│   └── package.json
│
├── backend/                # Backend Java/Spring Boot
│   ├── src/main/java/
│   │   ├── controller/    # Controllers REST
│   │   ├── service/       # Lógica de negócio
│   │   ├── model/         # Modelos de dados
│   │   └── config/        # Configurações
│   └── pom.xml
│
└── README.md
```

## Pré-requisitos

- [Node.js 18+](https://nodejs.org/)
- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [API Key da ExchangeRate-API](https://www.exchangerate-api.com/)

## Instalação

### Backend (Spring Boot)

```bash
cd backend

# Configure a API key
cp .env.example .env
# Edite .env com sua chave

# Execute
./mvnw spring-boot:run
# ou
mvn spring-boot:run

# A API estará em: http://localhost:8080
```

### Frontend (Next.js)

```bash
cd frontend

# Instale as dependências
npm install

# Configure a URL da API
cp .env.example .env.local
# Edite .env.local (padrão: http://localhost:8080)

# Execute
npm run dev

# O frontend estará em: http://localhost:3000
```

## API Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/currencies` | Lista moedas disponíveis |
| POST | `/api/convert` | Converte moedas |
| GET | `/api/rate?from=USD&to=BRL` | Busca taxa de câmbio |
| GET | `/api/health` | Status da API |

### Exemplo de Requisição

```bash
# Converter 100 USD para BRL
curl -X POST http://localhost:8080/api/convert \
  -H "Content-Type: application/json" \
  -d '{"amount": 100, "from": "USD", "to": "BRL"}'
```

### Exemplo de Resposta

```json
{
  "amount": 100,
  "from": "USD",
  "to": "BRL",
  "exchangeRate": 4.923400,
  "result": 492.34,
  "timestamp": "2024-01-15T10:30:00"
}
```

## Deploy

### Backend (Vercel/Railway/Render)

```bash
cd backend
mvn clean package
# Deploy o JAR gerado em backend/target/
```

### Frontend (Vercel)

```bash
cd frontend
npm i -g vercel
vercel login
vercel
```

## Tecnologias

### Frontend
- **Next.js 14** - Framework React com App Router
- **React 18** - Biblioteca de interfaces
- **TypeScript** - Tipagem estática
- **Tailwind CSS** - Estilização utility-first

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.2** - Framework web
- **Maven** - Gerenciador de dependências
- **ExchangeRate-API** - API de taxas de câmbio

## License

MIT
