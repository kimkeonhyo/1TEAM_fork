package com.example.backend.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class StockWebSocketHandler extends TextWebSocketHandler {

    // 연결된 WebSocket 세션 저장
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            sessions.add(session);
            log.info("[LOG] 새로운 WebSocket 연결: {}", session.getId());
        } catch (Exception e) {
            log.error("[ERROR] WebSocket 연결 중 오류 발생:", e);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 필요 시 클라이언트 메시지 처리
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
    }

    // Redis 업데이트 발생 시 모든 세션에 데이터 전송
    public void broadcastMessage(String message) {
        sessions.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}