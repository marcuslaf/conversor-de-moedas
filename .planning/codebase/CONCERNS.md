# Codebase Concerns
**Analysis Date:** 2026-07-17

## Tech Debt — P0 (Crítico)

### 1. Código Fonte Vazio
- **Problema:** `src/CurrencyConverter.java` é um stub de 4 linhas sem nenhuma lógica implementada
- **Arquivo:** `src/CurrencyConverter.java`
- **Impacto:** A aplicação não funciona. O projeto inteiro é não-funcional.
- **Fix:** Implementar toda a lógica descrita no README (GUI, API, conversão)

### 2. Package Name Inválido
- **Problema:** `package PACKAGE_NAME;` — literal placeholder, não é um package Java válido
- **Arquivo:** `src/CurrencyConverter.java:1`
- **Impacto:** Código não compila. `PACKAGE_NAME` precisa ser substituído (ex: `com.conversormoedas`)
- **Fix:** Renomear para package válido e mover arquivo para diretório correspondente

### 3. Sem Gerenciador de Dependências
- **Problema:** README documenta Maven/Gradle mas não existe `pom.xml` nem `build.gradle`
- **Arquivo:** (ausente)
- **Impacto:** Gson não pode ser resolvido automaticamente. Build depende de configuração manual no IntelliJ
- **Fix:** Criar `pom.xml` ou `build.gradle` com dependência Gson

## Tech Debt — P1 (Alto)

### 4. Incompatibilidade de Versão Java
- **Problema:** IntelliJ configura JDK 25 (`openjdk-25`) mas README diz "Java 8+"
- **Arquivo:** `.idea/misc.xml:3`
- **Impacto:** Código pode usar features incompatíveis com Java 8 se desenvolvido com JDK 25
- **Fix:** Padronizar: ou documenta JDK 25 ou restringe código a Java 8 APIs

### 5. Ausência de Testes
- **Problema:** Zero arquivos de teste no projeto
- **Arquivo:** (ausente)
- **Impacto:** Sem verificação de regressão, sem validação de lógica de conversão
- **Fix:** Criar testes unitáriosJUnit/TestNG para lógica de conversão e parsing JSON

### 6. API Key Exposta no README
- **Problema:** Exemplo de URL no README contém API key real: `86387097a157d1b3a20362d4`
- **Arquivo:** `README.md:220`
- **Impacto:** Security risk — chave pode ser usada por terceiros
- **Fix:** Remover chave do README, usar variável de ambiente ou placeholder

## Tech Debt — P2 (Médio)

### 7. Arquitetura Single-File
- **Problema:** Toda lógica (UI, API, modelo, controller) planejada para um único arquivo
- **Arquivo:** `src/CurrencyConverter.java`
- **Impacto:** Difícil manutenção, testabilidade zero, violação SRP
- **Fix:** Separar em pelo menos 3 classes: `CurrencyConverterApp` (main), `ExchangeRateService` (API), `CurrencyUI` (Swing)

### 8. README Não Reflete Realidade
- **Problema:** README descreve app completa com features, screenshots, mas código está vazio
- **Arquivo:** `README.md`
- **Impacto:** Engana desenvolvedores que clonam o repo esperando código funcional
- **Fix:** Atualizar README para refletir estado real ou implementar o código

### 9. Sem Build Reproduzível
- **Problema:** Projeto só compila via IntelliJ IDEA — sem CLI build
- **Arquivo:** (ausente)
- **Impacto:** Impossível buildar em CI/CD, Docker, ou máquinas sem IntelliJ
- **Fix:** Adicionar build tool (Maven/Gradle) com wrapper

## Security Considerations

### API Key Management
- **Risco:** Chave da ExchangeRate-API está hardcoded no README
- **Recomendação:** Usar variável de ambiente `EXCHANGE_RATE_API_KEY` e ler via `System.getenv()`

## Quality Notes

### O Que o README Faz Bem (Referência para Implementação)
- Documentação completa e bem estruturada
- Descreve arquitetura clara (MVC simplificado)
- Lista de moedas bem definida
- Exemplo de request/response da API
- Possíveis melhorias futuras documentadas
