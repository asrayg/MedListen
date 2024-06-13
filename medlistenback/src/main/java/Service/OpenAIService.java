package Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

@Service
public class OpenAIService {
    private final String apiKey = "YOUR_OPENAI_API_KEY";
    private final RestTemplate restTemplate = new RestTemplate();

    public String getExplanation(String medicalText) {
        String prompt = "Explain the following medical information in simple terms: " + medicalText;
        return callOpenAIApi(prompt);
    }

    public String getResponse(String conversationId, String message, Map<String, String> context) {
        String prompt = createPromptWithContext(message, context);
        return callOpenAIApi(prompt);
    }

    private String createPromptWithContext(String message, Map<String, String> context) {
        StringBuilder prompt = new StringBuilder("The following is a conversation with a medical assistant. The assistant provides simple explanations and advice.\n");
        context.forEach((key, value) -> prompt.append(key).append(": ").append(value).append("\n"));
        prompt.append("User: ").append(message).append("\nAssistant:");
        return prompt.toString();
    }

    private String callOpenAIApi(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, String> body = Map.of("prompt", prompt, "max_tokens", "150");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.openai.com/v1/engines/davinci-codex/completions", entity, String.class);

        return response.getBody();
    }
}
