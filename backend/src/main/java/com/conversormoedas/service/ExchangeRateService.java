package com.conversormoedas.service;

import com.conversormoedas.model.ConversionResponse;
import com.conversormoedas.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ExchangeRateService {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;

    public ExchangeRateService(
            RestTemplate restTemplate,
            @Value("${exchange-rate.api.key}") String apiKey,
            @Value("${exchange-rate.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public ConversionResponse convert(BigDecimal amount, Currency from, Currency to) {
        if (from == to) {
            return ConversionResponse.create(amount, from, to, BigDecimal.ONE);
        }

        BigDecimal rate = getExchangeRate(from, to);
        return ConversionResponse.create(amount, from, to, rate);
    }

    @Cacheable(value = "exchangeRates", key = "#from.name() + '_' + #to.name()")
    public BigDecimal getExchangeRate(Currency from, Currency to) {
        if (from == to) {
            return BigDecimal.ONE;
        }

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment("latest", from.name())
                .queryParam("apiKey", apiKey)
                .toUriString();

        log.info("Buscando taxa de câmbio: {} -> {}", from, to);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !"success".equals(response.get("result"))) {
            String errorType = response != null
                    ? (String) response.getOrDefault("error-type", "desconhecido")
                    : "resposta nula";
            throw new RuntimeException("Erro ao buscar taxa de câmbio: " + errorType);
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> rates = (Map<String, Object>) response.get("conversion_rates");

        if (rates == null || !rates.containsKey(to.name())) {
            throw new RuntimeException("Moeda de destino não encontrada: " + to.name());
        }

        BigDecimal rate = new BigDecimal(rates.get(to.name()).toString());
        log.info("Taxa obtida: 1 {} = {} {}", from, rate, to);

        return rate;
    }
}
