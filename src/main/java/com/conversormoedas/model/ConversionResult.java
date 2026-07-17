package com.conversormoedas.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionResult {

    private final BigDecimal originalAmount;
    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final BigDecimal exchangeRate;
    private final BigDecimal convertedAmount;
    private final LocalDateTime timestamp;

    public ConversionResult(BigDecimal originalAmount, Currency fromCurrency,
                           Currency toCurrency, BigDecimal exchangeRate) {
        this.originalAmount = originalAmount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.exchangeRate = exchangeRate;
        this.convertedAmount = originalAmount.multiply(exchangeRate)
                .setScale(2, RoundingMode.HALF_UP);
        this.timestamp = LocalDateTime.now();
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedOriginal() {
        return fromCurrency.getSymbol() + " " + formatNumber(originalAmount);
    }

    public String getFormattedConverted() {
        return toCurrency.getSymbol() + " " + formatNumber(convertedAmount);
    }

    public String getFormattedRate() {
        return "1 " + fromCurrency.name() + " = " +
               exchangeRate.setScale(4, RoundingMode.HALF_UP) + " " + toCurrency.name();
    }

    public String getSummary() {
        return getFormattedOriginal() + " = " + getFormattedConverted();
    }

    public String getDetailedSummary() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format(
            "%s (%s)\n" +
            "↓\n" +
            "%s (%s)\n\n" +
            "Taxa: %s\n" +
            "Data: %s",
            getFormattedOriginal(), fromCurrency.getDisplayName(),
            getFormattedConverted(), toCurrency.getDisplayName(),
            getFormattedRate(),
            timestamp.format(formatter)
        );
    }

    private String formatNumber(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_UP)
                .toPlainString()
                .replaceAll("(\\d)(?=(\\d{3})+(?!\\d))", "$1.");
    }

    @Override
    public String toString() {
        return getSummary();
    }
}
