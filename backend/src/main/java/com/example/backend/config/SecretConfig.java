package com.example.backend.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableEncryptableProperties  // 이 어노테이션이 있어야 암호화된 프로퍼티를 사용할 수 있습니다.
public class SecretConfig {

    @Value("${jasypt.encryptor.password}")
    private String key; // 암호화 시 사용될 키 값

    @Bean(name="jasyptEncryptorDES")  // 빈 이름을 "jasyptEncryptorDES"로 설정
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);  // 암호화 키 설정
        config.setAlgorithm("PBEWithMD5AndDES");  // 알고리즘 설정
        config.setKeyObtentionIterations("1000");  // 해싱 반복 설정
        config.setPoolSize("1");  // Pool 사이즈 설정
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");  // Salt 생성 방식 설정
        config.setStringOutputType("base64");  // 인코딩 방식 설정
        encryptor.setConfig(config);
        return encryptor;
    }
}
