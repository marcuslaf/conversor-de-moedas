package com.conversormoedas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ConversorMoedasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConversorMoedasApplication.class, args);
    }
}
