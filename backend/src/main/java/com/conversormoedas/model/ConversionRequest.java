package com.conversormoedas.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ConversionRequest {

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal amount;

    @NotNull(message = "Moeda de origem é obrigatória")
    private Currency from;

    @NotNull(message = "Moeda de destino é obrigatória")
    private Currency to;

    public ConversionRequest() {
    }

    public ConversionRequest(BigDecimal amount, Currency from, Currency to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
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
}
