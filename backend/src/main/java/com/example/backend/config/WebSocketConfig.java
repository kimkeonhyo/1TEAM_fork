package com.example.backend.config;

import com.example.backend.websocket.StockWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(stockWebSocketHandler(), "/ws/stock")

                .setAllowedOrigins("https://1thebest.site"); // HTTP 도메인 사용
                //.setAllowedOrigins("http://localhost:3000"); // 로컬테스트 시 사용

    }

    @Bean
    public StockWebSocketHandler stockWebSocketHandler() {
        return new StockWebSocketHandler();
    }
}