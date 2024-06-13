package com.example.medicalapp.controller;

import com.example.medicalapp.service.MLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ml")
public class MLController {
    @Autowired
    private MLService mlService;

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        String answer = mlService.askQuestion(question);
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
