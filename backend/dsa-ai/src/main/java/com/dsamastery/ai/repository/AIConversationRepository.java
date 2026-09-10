package com.dsamastery.ai.repository;

import com.dsamastery.ai.entity.AIConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AIConversationRepository extends JpaRepository<AIConversation, UUID> {

    List<AIConversation> findByProblemIdOrderByLastMessageAtDesc(UUID problemId);

    List<AIConversation> findAllByOrderByLastMessageAtDesc();
}