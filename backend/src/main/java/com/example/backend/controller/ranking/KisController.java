package com.example.backend.controller.ranking;

import com.example.backend.repository.ranking.ResponseDTO;
import com.example.backend.repository.ranking.ResponseOutputDTO;
import com.example.backend.service.ranking.KisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import com.example.backend.service.KisTokenService;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
@Slf4j
@RestController
public class KisController {

    private KisService kisService;
    private final KisTokenService kisTokenService;

    @Autowired
    public KisController(KisService kisService, KisTokenService kisTokenService) {
        this.kisService = kisService;
        this.kisTokenService = kisTokenService;
    }


    @GetMapping("/volume-rank")
public Mono<List<ResponseOutputDTO>> getVolumeRank() {
    return Mono.fromCallable(() -> {
        try {
            String accessToken = kisTokenService.getCachedAccessToken();
            return kisService.getVolumeRank(accessToken);
        } catch (Exception e) {
            log.error("Error getting access token", e);
            return Mono.<List<ResponseOutputDTO>>error(e);
        }
    }).flatMap(mono -> mono);
}


}