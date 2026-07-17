package com.conversormoedas.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@Configuration
public class LoggingConfig implements WebMvcConfigurer {

    private static final Logger requestLogger = LoggerFactory.getLogger("REQUEST_LOGGER");

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLoggingInterceptor())
                .addPathPatterns("/api/**");
    }

    private static class RequestLoggingInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Object handler) {
            String requestId = UUID.randomUUID().toString().substring(0, 8);
            request.setAttribute("requestId", requestId);
            request.setAttribute("startTime", System.currentTimeMillis());

            requestLogger.info("[{}] {} {} - IP: {}",
                    requestId,
                    request.getMethod(),
                    request.getRequestURI(),
                    getClientIp(request));

            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object handler,
                                    Exception ex) {
            String requestId = (String) request.getAttribute("requestId");
            long startTime = (long) request.getAttribute("startTime");
            long duration = System.currentTimeMillis() - startTime;

            String level = response.getStatus() >= 400 ? "WARN" : "INFO";

            if ("INFO".equals(level)) {
                requestLogger.info("[{}] {} {} - Status: {} - {}ms",
                        requestId,
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus(),
                        duration);
            } else {
                requestLogger.warn("[{}] {} {} - Status: {} - {}ms",
                        requestId,
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus(),
                        duration);
            }

            if (ex != null) {
                requestLogger.error("[{}] Error: {}", requestId, ex.getMessage(), ex);
            }
        }

        private String getClientIp(HttpServletRequest request) {
            String xForwardedFor = request.getHeader("X-Forwarded-For");
            if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                return xForwardedFor.split(",")[0].trim();
            }
            return request.getRemoteAddr();
        }
    }
}
