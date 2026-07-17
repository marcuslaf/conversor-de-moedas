package com.conversormoedas.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    @Test
    void shouldReturnCurrencyFromValidCode() {
        assertEquals(Currency.USD, Currency.fromCode("USD"));
        assertEquals(Currency.BRL, Currency.fromCode("BRL"));
        assertEquals(Currency.EUR, Currency.fromCode("eur"));
    }

    @Test
    void shouldThrowExceptionForInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> Currency.fromCode("INVALID"));
        assertThrows(IllegalArgumentException.class, () -> Currency.fromCode(""));
        assertThrows(IllegalArgumentException.class, () -> Currency.fromCode(null));
    }

    @Test
    void shouldReturnCorrectDisplayName() {
        assertEquals("Dólar Americano", Currency.USD.getDisplayName());
        assertEquals("Real Brasileiro", Currency.BRL.getDisplayName());
    }

    @Test
    void shouldReturnCorrectSymbol() {
        assertEquals("$", Currency.USD.getSymbol());
        assertEquals("R$", Currency.BRL.getSymbol());
        assertEquals("€", Currency.EUR.getSymbol());
    }

    @Test
    void shouldReturnCodeOnToString() {
        assertEquals("USD", Currency.USD.name());
        assertEquals("BRL", Currency.BRL.name());
    }
}
