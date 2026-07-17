package com.conversormoedas.controller;

import com.conversormoedas.model.ConversionRequest;
import com.conversormoedas.model.ConversionResponse;
import com.conversormoedas.model.Currency;
import com.conversormoedas.service.ExchangeRateService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final ExchangeRateService exchangeRateService;

    public CurrencyController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<Map<String, String>>> getCurrencies() {
        List<Map<String, String>> currencies = Arrays.stream(Currency.values())
                .map(c -> Map.of(
                        "code", c.name(),
                        "name", c.getDisplayName(),
                        "symbol", c.getSymbol()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(currencies);
    }

    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(@Valid @RequestBody ConversionRequest request) {
        log.info("Convertendo {} {} para {}", request.getAmount(), request.getFrom(), request.getTo());

        ConversionResponse response = exchangeRateService.convert(
                request.getAmount(),
                request.getFrom(),
                request.getTo()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rate")
    public ResponseEntity<Map<String, Object>> getRate(
            @RequestParam Currency from,
            @RequestParam Currency to) {

        BigDecimal rate = exchangeRateService.getExchangeRate(from, to);

        return ResponseEntity.ok(Map.of(
                "from", from.name(),
                "to", to.name(),
                "rate", rate
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Conversor de Moedas API"
        ));
    }
}
