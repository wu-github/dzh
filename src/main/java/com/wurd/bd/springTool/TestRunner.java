package com.wurd.bd.springTool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestRunner implements CommandLineRunner {

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public void run(String... args) throws Exception {
        log.info("profile: {}", profile);
    }
}
