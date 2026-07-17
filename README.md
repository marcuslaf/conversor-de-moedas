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
- Deploy com Docker

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
│   ├── Dockerfile
│   └── package.json
│
├── backend/                # Backend Java/Spring Boot
│   ├── src/main/java/
│   │   ├── controller/    # Controllers REST
│   │   ├── service/       # Lógica de negócio
│   │   ├── model/         # Modelos de dados
│   │   └── config/        # Configurações
│   ├── Dockerfile
│   └── pom.xml
│
├── docker-compose.yml
└── README.md
```

## Pré-requisitos

- [Node.js 18+](https://nodejs.org/)
- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [Docker](https://www.docker.com/) (opcional)
- [API Key da ExchangeRate-API](https://www.exchangerate-api.com/)

## Instalação Local

### 1. Obtenha a API Key

1. Acesse [ExchangeRate-API](https://www.exchangerate-api.com/)
2. Crie uma conta gratuita
3. Copie sua API key

### 2. Backend (Spring Boot)

```bash
cd backend

# Configure a API key (Linux/Mac)
export EXCHANGE_RATE_API_KEY=sua_chave_aqui

# Ou no Windows (PowerShell)
$env:EXCHANGE_RATE_API_KEY="sua_chave_aqui"

# Execute
./mvnw spring-boot:run
# A API estará em: http://localhost:8080
```

### 3. Frontend (Next.js)

```bash
cd frontend

# Instale as dependências
npm install

# Execute
npm run dev
# O frontend estará em: http://localhost:3000
```

## Deploy com Docker

### Local

```bash
# Clone e configure
git clone https://github.com/marcuslaf/conversor-de-moedas.git
cd conversor-de-moedas

# Configure a API key
export EXCHANGE_RATE_API_KEY=sua_chave_aqui

# Execute com Docker Compose
docker-compose up --build
```

Acesse:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

## Deploy em Produção

### Opção 1: Vercel (Frontend) + Railway (Frontend)

**Frontend na Vercel:**
1. Faça push para o GitHub
2. Acesse [vercel.com](https://vercel.com)
3. Importe o repositório
4. Configure:
   - Root Directory: `frontend`
   - Build Command: `npm run build`
   - Output Directory: `.next`
5. Adicione variável de ambiente:
   - `NEXT_PUBLIC_API_URL` = URL do backend Railway

**Backend no Railway:**
1. Acesse [railway.app](https://railway.app)
2. Crie um novo projeto
3. Adicione o repositório
4. Configure:
   - Root Directory: `backend`
   - Build Command: `mvn clean package -DskipTests`
   - Start Command: `java -jar target/*.jar`
5. Adicione variável de ambiente:
   - `EXCHANGE_RATE_API_KEY` = sua chave
   - `SPRING_PROFILES_ACTIVE` = `prod`

### Opção 2: Render

**Backend:**
1. Acesse [render.com](https://render.com)
2. Crie um "Web Service"
3. Configure:
   - Build Command: `cd backend && mvn clean package -DskipTests`
   - Start Command: `cd backend && java -jar target/*.jar`
4. Adicione variável de ambiente:
   - `EXCHANGE_RATE_API_KEY` = sua chave

**Frontend:**
1. Crie um "Static Site"
2. Configure:
   - Build Command: `cd frontend && npm install && npm run build`
   - Publish Directory: `frontend/.next/static`

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
