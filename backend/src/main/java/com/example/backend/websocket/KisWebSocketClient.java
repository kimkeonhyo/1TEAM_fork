package com.example.backend.websocket;

import com.example.backend.service.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.Response;
import okhttp3.RequestBody;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

@Slf4j
@Service
public class KisWebSocketClient {
    private final String WS_URL = "ws://ops.koreainvestment.com:21000/tryitout/H0STCNT0";

    private WebSocket webSocket;
    private String approvalKey;

    private final KafkaProducerService kafkaProducerService;

    // kafka producer 추가
    @Autowired
    public KisWebSocketClient(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }
     
    public void connect(String approvalKeyJson) {
        JSONObject jsonObject = new JSONObject(approvalKeyJson);
        this.approvalKey = jsonObject.getString("approval_key");
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
            .url(WS_URL)
            .addHeader("approval_key", approvalKey)            
            .addHeader("custtype", "P")                                  
            .addHeader("tr_type", "1") 
            .addHeader("content-type", "utf-8")
            .build();
            
        this.webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                log.info("[LOG] 웹소켓 연결 성공");
                String[] subscriptionlist = {}; //구독할 리스트(삼성전자, sk하이닉스)
                subscribeStocks(subscriptionlist);

                // // 테스트용 타이머 추가
                // Timer timer = new Timer();
                // timer.scheduleAtFixedRate(new TimerTask() {
                //     @Override
                //     public void run() {
                //         String testText = "0|H0STCNT0|002|005930^093354^71900^5^-100^-0.14^72023.83^72100^72400^71700^71900^71800^1^3052507^219853241700^5105^6937^1832^84.90^1366314^1159996^1^0.39^20.28^090020^5^-200^090820^5^-500^092619^2^200^20230612^20^N^65945^216924^1118750^2199206^0.05^2424142^125.92^0^^72100^005930^093354^72000^5^+500^-0.14^72023.83^72100^72400^71700^71900^71800^1^3052507^219853241700^5105^6937^1832^84.90^1366314^1159996^1^0.39^20.28^090020^5^-200^090820^5^-500^092619^2^200^20230612^20^N^65945^216924^1118750^2199206^0.05^2424142^125.92^0^^72100";
                //         onMessage(webSocket, testText);
                //     }
                // }, 0, 2000); // 2초마다 실행
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                try {
                    
                    //log.info("원본 데이터: {}", text);
                    // test용 데이터
                    // String text = "0|H0STCNT0|002|005930^093354^71900^5^-100^-0.14^72023.83^72100^72400^71700^71900^71800^1^3052507^219853241700^5105^6937^1832^84.90^1366314^1159996^1^0.39^20.28^090020^5^-200^090820^5^-500^092619^2^200^20230612^20^N^65945^216924^1118750^2199206^0.05^2424142^125.92^0^^72100^005930^093354^72000^5^+500^-0.14^72023.83^72100^72400^71700^71900^71800^1^3052507^219853241700^5105^6937^1832^84.90^1366314^1159996^1^0.39^20.28^090020^5^-200^090820^5^-500^092619^2^200^20230612^20^N^65945^216924^1118750^2199206^0.05^2424142^125.92^0^^72100";
                    // 데이터가 |로 구분되어 있으므로 분리
                    String[] data = text.split("\\|");

                    if (data.length > 3) {
                        String text_encryption = data[0];
                        String text_tr_id = data[1];
                        int text_data_number = Integer.parseInt(data[2]);
                        String text_response_messages = data[3];
 
                        String[] responsedata = text_response_messages.split("\\^");
                        
                        for (int i = 0; i < text_data_number; i++) {
                            // 종목 당 데이터가 46개!
                            String MKSC_SHRN_ISCD = responsedata[i * 46]; //유가증권 단축 종목코드
                            String STCK_CNTG_HOUR = responsedata[i * 46 + 1]; //주식 체결 시간
                            String STCK_PRPR = responsedata[i * 46 + 2]; //주식 현재가
                            String PRDY_VRSS_SIGN = responsedata[i * 46 + 3]; //전일 대비 부호
                            String PRDY_VRSS = responsedata[i * 46 + 4]; // 전일 대비
                            String PRDY_CTRT = responsedata[i * 46 + 5]; // 전일 대비율
                            String CNTG_VOL = responsedata[i * 46 + 12]; //체결 거래량
        
//                            // 테스트용
//                            log.info("유가증권 단축 종목코드(MKSC_SHRN_ISCD): {}, 주식 현재가(STCK_PRPR): {}, 주식 체결 시간(STCK_CNTG_HOUR): {}, 전일 대비 부호 (PRDY_VRSS_SIGN) : {}, 전일 대비율(PRDY_CTRT): {}, 체결 거래량(CNTG_VOL) : {}, 전일 대비(PRDY_VRSS) : {}",
//                                MKSC_SHRN_ISCD, STCK_PRPR, STCK_CNTG_HOUR, PRDY_VRSS_SIGN, PRDY_CTRT, CNTG_VOL, PRDY_VRSS);
//
//                            // 테스트용
//                            if (MKSC_SHRN_ISCD.equals("005930")) {
//                                log.info("삼성전자 현재가: {}, 시간: {}", STCK_PRPR, STCK_CNTG_HOUR);
//                            } else if (MKSC_SHRN_ISCD.equals("000660")) {
//                                log.info("sk하이닉스 현재가: {}, 시간: {}", STCK_PRPR, STCK_CNTG_HOUR);
//                            }
                            
                            // Kafka로 메시지 전송
                            String kafkaMessage = String.format(
                                    "{\"stockId\": \"%s\", \"currentPrice\": \"%s\", \"fluctuationPrice\": \"%s\",\"fluctuationRate\": \"%s\",\"fluctuationSign\": \"%s\", \"transactionVolume\": \"%s\", \"tradingTime\": \"%s\"}",
                                    MKSC_SHRN_ISCD, STCK_PRPR, PRDY_VRSS, PRDY_CTRT, PRDY_VRSS_SIGN, CNTG_VOL, STCK_CNTG_HOUR
                            );
                            
                            // 주식 종목에 따라 다른 토픽으로 전송
                            String topic = "realtime-data-" + MKSC_SHRN_ISCD; // 예: "realtime-data-005930"
                            kafkaProducerService.sendMessage(topic, kafkaMessage);
                            //log.info("Kafka로 전송: Topic={}, Message={}", topic, kafkaMessage);

                            // String testKafkaMessage = String.format(
                            //         "{\"stockId\": \"100000\", \"currentPrice\": \"100000\", \"fluctuationPrice\": \"100000\",\"fluctuationRate\": \"100000\",\"fluctuationSign\": \"100000\", \"transactionVolume\": \"100000\", \"tradingTime\": \"100000\", }"
                            // ); 

                            // kafkaProducerService.sendMessage(topic, testKafkaMessage);
                        }
        
                }

                } catch (Exception e) {
                   log.error("[ERROR] 데이터 처리 중 오류: ", e);
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                log.error("웹소켓 에러: ", t);
            }
        });
    }

    //종목 코드로 구독 요청 stockCodes 종목 코드
    public void subscribeStocks(String[] stockCodes) { // 리스트형식으로 받음
        if (webSocket == null) {
            log.error("[ERROR] WebSocket 연결 실패");
            return;
        }
        log.info("[LOG] WebSocket 연결 성공");

        String requestTemplate = "{\"header\":{\"approval_key\":\"%s\",\"custtype\":\"P\",\"tr_type\":\"1\",\"content-type\":\"utf-8\"},\"body\":{\"input\":{\"tr_id\":\"H0STCNT0\",\"tr_key\":\"%s\"}}}";

        for (String stockCode : stockCodes) {
            String request = String.format(requestTemplate, approvalKey, stockCode);
            webSocket.send(request);
            log.info("[LOG] 종목 코드 {} 구독 요청 전송", stockCode);
        }
    }

    // 구독 목록 업데이트
    public void updateSubscriptions(String[] newStockCodes) {
        subscribeStocks(newStockCodes);
        log.info("[LOG] 새로운 구독 목록 업데이트: {}", Arrays.toString(newStockCodes));
    }
}


