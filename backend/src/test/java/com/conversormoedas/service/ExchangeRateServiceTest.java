package com.conversormoedas.service;

import com.conversormoedas.model.ConversionResponse;
import com.conversormoedas.model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    private static final String API_KEY = "test-api-key";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6";

    @BeforeEach
    void setUp() {
        exchangeRateService = new ExchangeRateService(restTemplate, API_KEY, BASE_URL);
    }

    @Test
    void shouldReturnSameCurrencyWhenConvertingToItself() {
        ConversionResponse response = exchangeRateService.convert(
                new BigDecimal("100"), Currency.USD, Currency.USD
        );

        assertEquals(new BigDecimal("100"), response.getResult());
        assertEquals(BigDecimal.ONE, response.getExchangeRate());
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldConvertBetweenDifferentCurrencies() {
        Map<String, Object> apiResponse = Map.of(
                "result", "success",
                "conversion_rates", Map.of("BRL", 4.9234)
        );

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        ConversionResponse response = exchangeRateService.convert(
                new BigDecimal("100"), Currency.USD, Currency.BRL
        );

        assertEquals(new BigDecimal("492.34"), response.getResult());
        assertEquals(Currency.USD, response.getFrom());
        assertEquals(Currency.BRL, response.getTo());
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldThrowExceptionWhenApiFails() {
        Map<String, Object> apiResponse = Map.of(
                "result", "error",
                "error-type", "invalid-key"
        );

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        assertThrows(RuntimeException.class, () ->
                exchangeRateService.convert(new BigDecimal("100"), Currency.USD, Currency.BRL)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldThrowExceptionWhenCurrencyNotFound() {
        Map<String, Object> apiResponse = Map.of(
                "result", "success",
                "conversion_rates", Map.of("EUR", 0.92)
        );

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(apiResponse);

        assertThrows(RuntimeException.class, () ->
                exchangeRateService.convert(new BigDecimal("100"), Currency.USD, Currency.BRL)
        );
    }

    @Test
    void shouldReturnOneRateForSameCurrency() {
        BigDecimal rate = exchangeRateService.getExchangeRate(Currency.USD, Currency.USD);
        assertEquals(BigDecimal.ONE, rate);
    }

    private <T> Class<T> eq(Class<T> clazz) {
        return clazz;
    }
}
