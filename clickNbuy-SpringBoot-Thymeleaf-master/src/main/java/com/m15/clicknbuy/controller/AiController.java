package com.m15.clicknbuy.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m15.clicknbuy.service.AiService;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private static final Logger logger = LoggerFactory.getLogger(AiController.class);

    @Autowired
    private AiService aiService;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("response", "Message cannot be empty."));
        }

        try {
            String aiResponse = aiService.getAiResponse(message);
            return ResponseEntity.ok(Map.of("response", aiResponse));
        } catch (Exception e) {
            logger.error("Error in AI Chat: ", e);
            return ResponseEntity.status(500).body(Map.of("response", "I'm having a little trouble right now. Please try again later! 😅"));
        }
    }
}
