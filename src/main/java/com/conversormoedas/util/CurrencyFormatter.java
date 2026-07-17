package com.conversormoedas.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyFormatter {

    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols(LOCALE_BR);
    private static final DecimalFormat CURRENCY_FORMAT;
    private static final DecimalFormat RATE_FORMAT;
    private static final DecimalFormat INTEGER_FORMAT;

    static {
        CURRENCY_FORMAT = new DecimalFormat("#,##0.00", SYMBOLS);
        RATE_FORMAT = new DecimalFormat("0.0000", SYMBOLS);
        INTEGER_FORMAT = new DecimalFormat("#,##0", SYMBOLS);
    }

    public static String formatCurrency(BigDecimal amount, String currencySymbol) {
        if (amount == null || currencySymbol == null) {
            return "R$ 0,00";
        }
        String formatted = CURRENCY_FORMAT.format(amount);
        return currencySymbol + " " + formatted;
    }

    public static String formatCurrency(BigDecimal amount) {
        return formatCurrency(amount, "R$");
    }

    public static String formatRate(BigDecimal rate) {
        if (rate == null) {
            return "0,0000";
        }
        return RATE_FORMAT.format(rate);
    }

    public static String formatInteger(BigDecimal value) {
        if (value == null) {
            return "0";
        }
        return INTEGER_FORMAT.format(value.setScale(0, RoundingMode.DOWN));
    }

    public static String formatWithSign(BigDecimal amount, String symbol) {
        if (amount == null || symbol == null) {
            return symbol + " 0,00";
        }
        String formatted = CURRENCY_FORMAT.format(amount.abs());
        String sign = amount.compareTo(BigDecimal.ZERO) < 0 ? "-" : "";
        return sign + symbol + " " + formatted;
    }

    public static String formatPercentage(BigDecimal value) {
        if (value == null) {
            return "0%";
        }
        BigDecimal percent = value.multiply(new BigDecimal("100"))
                .setScale(1, RoundingMode.HALF_UP);
        return percent + "%";
    }

    public static String formatCompact(BigDecimal value) {
        if (value == null) {
            return "0";
        }

        double doubleValue = value.doubleValue();

        if (Math.abs(doubleValue) >= 1_000_000) {
            return String.format("%.1fM", doubleValue / 1_000_000);
        } else if (Math.abs(doubleValue) >= 1_000) {
            return String.format("%.1fK", doubleValue / 1_000);
        } else {
            return CURRENCY_FORMAT.format(value);
        }
    }
}
