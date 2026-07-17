package com.conversormoedas.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class ConversionResponse {

    private BigDecimal amount;
    private Currency from;
    private Currency to;
    private BigDecimal exchangeRate;
    private BigDecimal result;
    private LocalDateTime timestamp;

    public ConversionResponse() {
    }

    public ConversionResponse(BigDecimal amount, Currency from, Currency to,
                             BigDecimal exchangeRate, BigDecimal result, LocalDateTime timestamp) {
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.exchangeRate = exchangeRate;
        this.result = result;
        this.timestamp = timestamp;
    }

    public static ConversionResponse create(BigDecimal amount, Currency from, Currency to,
                                           BigDecimal exchangeRate) {
        BigDecimal result = amount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);

        return new ConversionResponse(
                amount,
                from,
                to,
                exchangeRate.setScale(6, RoundingMode.HALF_UP),
                result,
                LocalDateTime.now()
        );
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
