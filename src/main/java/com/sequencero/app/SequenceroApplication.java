package com.sequencero.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SequenceroApplication {
    public static void main(String[] args) {
        SpringApplication.run(SequenceroApplication.class, args);
    }
}
