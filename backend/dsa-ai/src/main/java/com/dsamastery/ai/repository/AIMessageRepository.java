package com.dsamastery.ai.repository;

import com.dsamastery.ai.entity.AIMessage;
import com.dsamastery.common.enums.MessageRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AIMessageRepository extends JpaRepository<AIMessage, UUID> {

    List<AIMessage> findByConversationIdOrderBySequenceAsc(UUID conversationId);

    @Query("SELECT MAX(m.sequence) FROM AIMessage m WHERE m.conversation.id = :conversationId")
    Optional<Integer> findMaxSequenceByConversationId(UUID conversationId);

    long countByConversationIdAndRole(UUID conversationId, MessageRole role);
}