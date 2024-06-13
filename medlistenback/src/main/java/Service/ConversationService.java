package Service;

import com.example.medlistenback.MedlistenbackApplication.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;

    public String startConversation(String userId, String initialMessage) {
        String conversationId = UUID.randomUUID().toString();
        Map<String, String> context = new HashMap<>();
        context.put("initialMessage", initialMessage);

        Conversation conversation = new Conversation();
        conversation.setConversationId(conversationId);
        conversation.setUserId(userId);
        conversation.setContext(new Gson().toJson(context));

        conversationRepository.save(conversation);
        return conversationId;
    }

    public Map<String, String> getContext(String conversationId) {
        return conversationRepository.findByConversationId(conversationId)
                .map(conversation -> new Gson().fromJson(conversation.getContext(), Map.class))
                .orElse(new HashMap<>());
    }

    public void updateContext(String conversationId, String key, String value) {
        conversationRepository.findByConversationId(conversationId).ifPresent(conversation -> {
            Map<String, String> context = new Gson().fromJson(conversation.getContext(), Map.class);
            context.put(key, value);
            conversation.setContext(new Gson().toJson(context));
            conversationRepository.save(conversation);
        });
    }
}
