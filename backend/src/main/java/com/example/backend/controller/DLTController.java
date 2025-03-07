package com.example.backend.controller;

import com.example.backend.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DLTController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // 메시지 전송 API (POST 요청)
    @PostMapping("/api/send")
    public String sendRDSMessage(@RequestParam String topic, @RequestBody String message) {
        kafkaProducerService.sendMessage(topic, message);
        return "Message sent to Kafka main-topic";
    }
}
