package com.conversormoedas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Currency {

    USD("Dólar Americano", "$"),
    BRL("Real Brasileiro", "R$"),
    EUR("Euro", "€"),
    GBP("Libra Esterlina", "£"),
    JPY("Iene Japonês", "¥"),
    CAD("Dólar Canadense", "C$"),
    AUD("Dólar Australiano", "A$"),
    CHF("Franco Suíço", "Fr"),
    CNY("Yuan Chinês", "¥"),
    INR("Rúpia Indiana", "₹");

    private final String displayName;
    private final String symbol;

    Currency(String displayName, String symbol) {
        this.displayName = displayName;
        this.symbol = symbol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    @JsonCreator
    public static Currency fromCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Código da moeda não pode ser nulo ou vazio");
        }
        try {
            return valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Moeda desconhecida: " + code);
        }
    }

    @JsonValue
    public String toCode() {
        return name();
    }
}
