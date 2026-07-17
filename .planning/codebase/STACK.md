# Technology Stack
**Analysis Date:** 2026-07-17

## Languages
- **Java** (configured: JDK 25 in IntelliJ, documented: Java 8+ compatible) - Linguagem principal e única

## Frameworks & UI
- **Java Swing** - Framework GUI desktop (built-in JDK)
- **SwingWorker** - Operações assíncronas na UI (built-in JDK)

## Key Dependencies
- **Gson 2.10.1** (Google) - Parsing de JSON para deserializar respostas da API de câmbio
  - Referenciado no README mas **sem gerenciador de dependências configurado** (sem pom.xml/build.gradle)

## API Externa
- **ExchangeRate-API v6** - API REST para taxas de câmbio em tempo real
  - Endpoint: `https://v6.exchangerate-api.com/v6/{API_KEY}/latest/{BASE_CURRENCY}`
  - Plano gratuito: 1.500 req/mês, taxas atualizadas diariamente
  - Suporta 160+ moedas

## IDE & Tooling
- **IntelliJ IDEA** - IDE de desenvolvimento
  - JDK: `openjdk-25` (configurado em `.idea/misc.xml`)
  - Projeto módulo único: `Conversor.iml`
- **Git** - Controle de versão

## Build System
- **Nenhum configurado** - Projeto depende exclusivamente do IntelliJ IDEA para compilação
  - README documenta opções Maven (pom.xml) e Gradle (build.gradle) mas nenhum existe no repo
  - JAR manual do Gson é uma opção documentada

## What's NOT Here
- Sem framework web (não é React/Next/Vue/Angular)
- Sem frontend browser - é aplicação desktop Java
- Sem sistema de build automatizado (CI/CD)
- Sem testes unitários
- Sem package manager funcional
