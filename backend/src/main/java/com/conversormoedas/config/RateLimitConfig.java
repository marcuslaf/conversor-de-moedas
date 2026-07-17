package com.conversormoedas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class RateLimitConfig {

    private static final int MAX_REQUESTS_PER_MINUTE = 60;
    private final Map<String, RateLimitEntry> requestCounts = new ConcurrentHashMap<>();

    @Bean
    public OncePerRequestFilter rateLimitFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain)
                    throws ServletException, IOException {

                String clientIp = getClientIp(request);
                long currentTime = System.currentTimeMillis();

                RateLimitEntry entry = requestCounts.compute(clientIp, (key, existing) -> {
                    if (existing == null || currentTime - existing.windowStart > 60000) {
                        return new RateLimitEntry(currentTime, new AtomicInteger(1));
                    }
                    existing.requestCount.incrementAndGet();
                    return existing;
                });

                if (entry.requestCount.get() > MAX_REQUESTS_PER_MINUTE) {
                    response.setStatus(429);
                    response.setContentType("application/json");
                    response.getWriter().write(
                        "{\"status\":429,\"error\":\"Rate Limit Exceeded\"," +
                        "\"message\":\"Muitas requisições. Limite: " + MAX_REQUESTS_PER_MINUTE +
                        " por minuto.\"}"
                    );
                    return;
                }

                response.setHeader("X-RateLimit-Limit", String.valueOf(MAX_REQUESTS_PER_MINUTE));
                response.setHeader("X-RateLimit-Remaining",
                        String.valueOf(MAX_REQUESTS_PER_MINUTE - entry.requestCount.get()));

                filterChain.doFilter(request, response);
            }

            private String getClientIp(HttpServletRequest request) {
                String xForwardedFor = request.getHeader("X-Forwarded-For");
                if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                    return xForwardedFor.split(",")[0].trim();
                }
                return request.getRemoteAddr();
            }
        };
    }

    private static class RateLimitEntry {
        final long windowStart;
        final AtomicInteger requestCount;

        RateLimitEntry(long windowStart, AtomicInteger requestCount) {
            this.windowStart = windowStart;
            this.requestCount = requestCount;
        }
    }
}
