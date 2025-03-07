package com.example.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class SlackNotificationService {
    @Value("${slack.web-hook-url}")
    private String slackWebhookUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessageToSlack(String message) throws JsonProcessingException {

        // JSON 데이터 생성
        Map<String, String> payload = new HashMap<>();
        payload.put("text", message);
        String jsonPayload = objectMapper.writeValueAsString(payload);

        // HTTP 헤더 설정
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청
        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
        restTemplate.postForEntity(slackWebhookUrl, request, String.class);

        log.info("[LOG] Message sent to Slack");
    }
}
