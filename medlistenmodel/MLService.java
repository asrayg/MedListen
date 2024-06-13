package com.example.medicalapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String flaskApiUrl = "http://localhost:5000/ask";

    public String askQuestion(String question) {
        Map<String, String> request = new HashMap<>();
        request.put("question", question);

        ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl, request, Map.class);
        return (String) response.getBody().get("answer");
    }
}
