package Repository;

import com.example.medlistenback.MedlistenbackApplication.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository {
    Optional<Conversation> findByConversationId(String conversationId);
}
