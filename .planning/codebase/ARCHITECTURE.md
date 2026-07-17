# Architecture
**Analysis Date:** 2026-07-17

## Pattern Overview
- **Overall:** Aplicação desktop monolítica single-file (planejada como MVC simplificado)
- **UI Framework:** Java Swing com GridBagLayout
- **Async Model:** SwingWorker para requisições HTTP não-bloqueantes

## File Tree
```
conversor-de-moedas/
├── .git/                          # Repositório Git
├── .gitignore                     # Ignora outputs IntelliJ/Eclipse/VSCode
├── .idea/                         # Configurações IntelliJ IDEA
│   ├── .gitignore
│   ├── misc.xml                   # JDK 25 (openjdk-25)
│   ├── modules.xml                # Módulo único: Conversor
│   └── vcs.xml                    # Mapeamento Git
├── Conversor.iml                  # Módulo IntelliJ (Java module)
├── README.md                      # Documentação completa (296 linhas)
└── src/
    └── CurrencyConverter.java     # Classe principal (STUB - 4 linhas)
```

## Critical Finding: Empty Implementation

O arquivo principal `src/CurrencyConverter.java` contém apenas:
```java
package PACKAGE_NAME;

public class CurrencyConverter {
}
```

**Isto é um stub vazio.** O README descreve uma aplicação completa com:
- GUI Swing com GridBagLayout
- 10 moedas (USD, BRL, EUR, GBP, JPY, CAD, AUD, CHF, CNY, INR)
- Integração HTTP com ExchangeRate-API
- SwingWorker para async
- Validação de entrada
- Botão de troca de moedas

Mas **nenhuma dessas funcionalidades está implementada no código fonte.**

## Layers (Planejadas pelo README)

| Camada | Responsabilidade | Status |
|--------|-----------------|--------|
| **UI (Swing)** | Interface gráfica com GridBagLayout | NÃO IMPLEMENTADA |
| **Service** | Requisição HTTP à ExchangeRate-API | NÃO IMPLEMENTADA |
| **Model** | Dados de moedas e taxas de câmbio | NÃO IMPLEMENTADA |
| **Controller** | Validação, orquestração, SwingWorker | NÃO IMPLEMENTADA |

## Data Flow (Planejado)
1. Usuário digita valor e seleciona moedas (UI)
2. Clica "Converter" → Controller valida entrada
3. Controller dispara SwingWorker → Service faz HTTP GET à API
4. Gson desserializa JSON response → Model armazena taxas
5. Controller calcula conversão → UI exibe resultado

## Dependencies Internas
- Nenhuma (single-file architecture)

## External Integrations
- ExchangeRate-API v6 (HTTP REST, JSON response)
