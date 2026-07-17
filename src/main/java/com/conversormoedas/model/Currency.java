package com.conversormoedas.model;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Currency {

    USD("Dólar Americano", "$", "🇺🇸"),
    BRL("Real Brasileiro", "R$", "🇧🇷"),
    EUR("Euro", "€", "🇪🇺"),
    GBP("Libra Esterlina", "£", "🇬🇧"),
    JPY("Iene Japonês", "¥", "🇯🇵"),
    CAD("Dólar Canadense", "C$", "🇨🇦"),
    AUD("Dólar Australiano", "A$", "🇦🇺"),
    CHF("Franco Suíço", "Fr", "🇨🇭"),
    CNY("Yuan Chinês", "¥", "🇨🇳"),
    INR("Rúpia Indiana", "₹", "🇮🇳");

    private final String displayName;
    private final String symbol;
    private final String flag;

    private static final Map<String, Currency> CODE_MAP = new LinkedHashMap<>();

    static {
        for (Currency currency : values()) {
            CODE_MAP.put(currency.name(), currency);
        }
    }

    Currency(String displayName, String symbol, String flag) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.flag = flag;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getFlag() {
        return flag;
    }

    public String getFullName() {
        return name() + " - " + displayName;
    }

    public String getFormattedName() {
        return flag + "  " + name() + " - " + displayName;
    }

    public static Currency fromCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Código da moeda não pode ser nulo ou vazio");
        }
        Currency currency = CODE_MAP.get(code.toUpperCase());
        if (currency == null) {
            throw new IllegalArgumentException("Moeda desconhecida: " + code);
        }
        return currency;
    }

    public static boolean isValidCode(String code) {
        return code != null && CODE_MAP.containsKey(code.toUpperCase());
    }

    @Override
    public String toString() {
        return getFormattedName();
    }
}
