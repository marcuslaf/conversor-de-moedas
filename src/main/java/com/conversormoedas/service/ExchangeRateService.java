package com.conversormoedas.service;

import com.conversormoedas.model.ConversionResult;
import com.conversormoedas.model.Currency;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateService {

    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6";
    private static final int TIMEOUT_SECONDS = 10;
    private static final int CACHE_DURATION_MINUTES = 30;

    private final HttpClient httpClient;
    private final Gson gson;
    private final String apiKey;
    private final Map<String, CacheEntry> cache;

    public ExchangeRateService() {
        this.apiKey = System.getenv("EXCHANGE_RATE_API_KEY");
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            throw new IllegalStateException(
                "EXCHANGE_RATE_API_KEY não configurada. " +
                "Defina a variável de ambiente com sua chave da ExchangeRate-API."
            );
        }

        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
        this.gson = new Gson();
        this.cache = new HashMap<>();
    }

    public ConversionResult convert(BigDecimal amount, Currency from, Currency to) throws IOException {
        if (from == to) {
            return new ConversionResult(amount, from, to, BigDecimal.ONE);
        }

        BigDecimal rate = getExchangeRate(from, to);
        return new ConversionResult(amount, from, to, rate);
    }

    public BigDecimal getExchangeRate(Currency from, Currency to) throws IOException {
        if (from == to) {
            return BigDecimal.ONE;
        }

        String cacheKey = from.name() + "_" + to.name();
        CacheEntry cached = cache.get(cacheKey);

        if (cached != null && !cached.isExpired()) {
            return cached.rate;
        }

        BigDecimal rate = fetchExchangeRate(from, to);
        cache.put(cacheKey, new CacheEntry(rate));
        return rate;
    }

    private BigDecimal fetchExchangeRate(Currency from, Currency to) throws IOException {
        String url = String.format("%s/%s/latest/%s", BASE_URL, apiKey, from.name());

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IOException("Erro na API: HTTP " + response.statusCode());
            }

            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

            if (!jsonResponse.has("result") || !"success".equals(jsonResponse.get("result").getAsString())) {
                String errorType = jsonResponse.has("error-type")
                        ? jsonResponse.get("error-type").getAsString()
                        : "desconhecido";
                throw new IOException("Erro na conversão: " + errorType);
            }

            JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");

            if (!rates.has(to.name())) {
                throw new IOException("Moeda de destino não encontrada: " + to.name());
            }

            return rates.get(to.name()).getAsBigDecimal();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Requisição interrompida", e);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Erro ao processar resposta da API: " + e.getMessage(), e);
        }
    }

    public boolean isApiKeyValid() {
        return apiKey != null && !apiKey.isEmpty() && !apiKey.equals("YOUR_API_KEY_HERE");
    }

    public void clearCache() {
        cache.clear();
    }

    private static class CacheEntry {
        final BigDecimal rate;
        final long timestamp;

        CacheEntry(BigDecimal rate) {
            this.rate = rate;
            this.timestamp = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_DURATION_MINUTES * 60 * 1000L;
        }
    }
}
