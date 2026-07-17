package com.conversormoedas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {

    private BigDecimal amount;
    private Currency from;
    private Currency to;
    private BigDecimal exchangeRate;
    private BigDecimal result;
    private LocalDateTime timestamp;

    public static ConversionResponse create(BigDecimal amount, Currency from, Currency to,
                                           BigDecimal exchangeRate) {
        BigDecimal result = amount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);

        return ConversionResponse.builder()
                .amount(amount)
                .from(from)
                .to(to)
                .exchangeRate(exchangeRate.setScale(6, RoundingMode.HALF_UP))
                .result(result)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
