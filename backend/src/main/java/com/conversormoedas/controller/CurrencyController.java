package com.conversormoedas.controller;

import com.conversormoedas.model.ConversionRequest;
import com.conversormoedas.model.ConversionResponse;
import com.conversormoedas.model.Currency;
import com.conversormoedas.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Currency", description = "API de conversão de moedas")
public class CurrencyController {

    private final ExchangeRateService exchangeRateService;

    public CurrencyController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/currencies")
    @Operation(summary = "Lista moedas disponíveis", description = "Retorna todas as moedas suportadas pela API")
    @ApiResponse(responseCode = "200", description = "Lista de moedas retornada com sucesso")
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
    @Operation(summary = "Converte moedas", description = "Converte um valor de uma moeda para outra usando taxas em tempo real")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversão realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao acessar API de câmbio")
    })
    public ResponseEntity<ConversionResponse> convert(
            @Valid @RequestBody ConversionRequest request) {
        log.info("Convertendo {} {} para {}", request.getAmount(), request.getFrom(), request.getTo());

        ConversionResponse response = exchangeRateService.convert(
                request.getAmount(),
                request.getFrom(),
                request.getTo()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rate")
    @Operation(summary = "Busca taxa de câmbio", description = "Retorna a taxa de câmbio entre duas moedas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Taxa retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Moedas inválidas")
    })
    public ResponseEntity<Map<String, Object>> getRate(
            @Parameter(description = "Moeda de origem") @RequestParam Currency from,
            @Parameter(description = "Moeda de destino") @RequestParam Currency to) {

        BigDecimal rate = exchangeRateService.getExchangeRate(from, to);

        return ResponseEntity.ok(Map.of(
                "from", from.name(),
                "to", to.name(),
                "rate", rate
        ));
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica se a API está funcionando")
    @ApiResponse(responseCode = "200", description = "API funcionando")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "Conversor de Moedas API"
        ));
    }
}
