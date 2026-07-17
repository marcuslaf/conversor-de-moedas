package com.conversormoedas.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ConversionResponseTest {

    @Test
    void shouldCreateConversionResponse() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal rate = new BigDecimal("4.9234");

        ConversionResponse response = ConversionResponse.create(amount, Currency.USD, Currency.BRL, rate);

        assertEquals(amount, response.getAmount());
        assertEquals(Currency.USD, response.getFrom());
        assertEquals(Currency.BRL, response.getTo());
        assertNotNull(response.getExchangeRate());
        assertNotNull(response.getResult());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void shouldCalculateResultCorrectly() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal rate = new BigDecimal("4.9234");

        ConversionResponse response = ConversionResponse.create(amount, Currency.USD, Currency.BRL, rate);

        assertEquals(new BigDecimal("492.34"), response.getResult());
    }

    @Test
    void shouldHandleZeroAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal rate = new BigDecimal("4.9234");

        ConversionResponse response = ConversionResponse.create(amount, Currency.USD, Currency.BRL, rate);

        assertEquals(BigDecimal.ZERO, response.getResult());
    }

    @Test
    void shouldScaleExchangeRateTo6Decimals() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal rate = new BigDecimal("4.9234");

        ConversionResponse response = ConversionResponse.create(amount, Currency.USD, Currency.BRL, rate);

        assertEquals(6, response.getExchangeRate().scale());
    }

    @Test
    void shouldScaleResultTo2Decimals() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal rate = new BigDecimal("4.923456");

        ConversionResponse response = ConversionResponse.create(amount, Currency.USD, Currency.BRL, rate);

        assertEquals(2, response.getResult().scale());
    }
}
