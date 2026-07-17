package com.conversormoedas.controller;

import com.conversormoedas.model.ConversionRequest;
import com.conversormoedas.model.ConversionResponse;
import com.conversormoedas.model.Currency;
import com.conversormoedas.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.bean.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnCurrenciesList() throws Exception {
        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].code").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].symbol").exists());
    }

    @Test
    void shouldConvertCurrency() throws Exception {
        ConversionRequest request = new ConversionRequest(
                new BigDecimal("100"), Currency.USD, Currency.BRL
        );

        ConversionResponse response = ConversionResponse.create(
                new BigDecimal("100"), Currency.USD, Currency.BRL, new BigDecimal("4.9234")
        );

        when(exchangeRateService.convert(any(), any(), any())).thenReturn(response);

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.from").value("USD"))
                .andExpect(jsonPath("$.to").value("BRL"))
                .andExpect(jsonPath("$.result").value(492.34));
    }

    @Test
    void shouldReturnErrorForInvalidRequest() throws Exception {
        ConversionRequest request = new ConversionRequest(null, null, null);

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnHealthCheck() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("Conversor de Moedas API"));
    }

    @Test
    void shouldGetExchangeRate() throws Exception {
        when(exchangeRateService.getExchangeRate(Currency.USD, Currency.BRL))
                .thenReturn(new BigDecimal("4.9234"));

        mockMvc.perform(get("/api/rate")
                        .param("from", "USD")
                        .param("to", "BRL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value("USD"))
                .andExpect(jsonPath("$.to").value("BRL"))
                .andExpect(jsonPath("$.rate").value(4.9234));
    }
}
