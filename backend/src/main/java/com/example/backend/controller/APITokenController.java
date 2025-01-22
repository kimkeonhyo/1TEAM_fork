package com.example.backend.controller;
import com.example.backend.entity.APIToken;
import com.example.backend.repository.APITokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/token")
public class APITokenController {

    @Autowired
    private APITokenRepository tokenRepository;

    // 1. 토큰 저장
    @PostMapping("/save")
    public String saveToken(@RequestParam String userId, @RequestParam String tokenValue) {
        APIToken token = new APIToken();
        token.setTokenValue(tokenValue);

        // 현재 시간 + 24시간 (만료 시간 설정)
        token.setExpirationTime(LocalDateTime.now().plusHours(24));

        tokenRepository.save(token);
        return "expires at: " + token.getExpirationTime();
    }

    // 2. 특정 토큰 값으로 유효성 확인
    @GetMapping("/validate-token")
    public String validateToken(@RequestParam String tokenValue) {
        Optional<APIToken> token = tokenRepository.findByTokenValue(tokenValue);

        if (token.isPresent()) {
            // 만료 시간 확인
            if (token.get().getExpirationTime().isAfter(LocalDateTime.now())) {
                return "Token is valid.";
            } else {
                return "Token is expired.";
            }
        } else {
            return "Token does not exist.";
        }
    }

    // 3. 만료된 토큰 삭제
    @DeleteMapping("/delete-expired")
    public String deleteExpiredTokens() {
        tokenRepository.deleteExpiredAPITokens();
        return "Expired tokens deleted!";
    }
}