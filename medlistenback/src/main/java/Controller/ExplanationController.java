package Controller;

import com.example.medlistenback.MedlistenbackApplication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/explain")
public class ExplanationController {
    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> startConversation(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String initialMessage = request.get("message");
        String conversationId = conversationService.startConversation(userId, initialMessage);
        String explanation = openAIService.getExplanation(initialMessage);

        Map<String, String> response = new HashMap<>();
        response.put("conversationId", conversationId);
        response.put("explanation", explanation);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/continue")
    public ResponseEntity<Map<String, String>> continueConversation(@RequestBody Map<String, String> request) {
        String conversationId = request.get("conversationId");
        String message = request.get("message");
        Map<String, String> context = conversationService.getContext(conversationId);

        String response = openAIService.getResponse(conversationId, message, context);
        conversationService.updateContext(conversationId, "lastMessage", message);

        Map<String, String> result = new HashMap<>();
        result.put("response", response);

        return ResponseEntity.ok(result);
    }
}
