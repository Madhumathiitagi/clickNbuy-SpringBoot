package com.m15.clicknbuy;

import com.m15.clicknbuy.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

// @SpringBootApplication
public class AiTestApp {
    public static void main(String[] args) {
        SpringApplication.run(AiTestApp.class, args);
    }

    @Autowired
    private AiService aiService;

//    @Bean
    public CommandLineRunner run() {
        return args -> {
            try {
                System.out.println("Testing AI Service...");
                String response = aiService.getAiResponse("Hello");
                System.out.println("AI Response: " + response);
            } catch (Exception e) {
                System.err.println("AI Service failed with exception:");
                e.printStackTrace();
            } finally {
                System.exit(0);
            }
        };
    }
}
