package com.example.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import com.example.backend.websocket.KisWebSocketClient;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private KisWebSocketClient kisWebSocketClient;

    @PostMapping("/update")
    public ResponseEntity<Void> updateSubscriptions(@RequestBody List<String> stockIds) {
        kisWebSocketClient.updateSubscriptions(stockIds.toArray(new String[0]));
        return ResponseEntity.ok().build();
    }
}