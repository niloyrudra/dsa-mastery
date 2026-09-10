package com.dsamastery.ai.entity;

import com.dsamastery.common.enums.ModelRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An AI tutor conversation. Persists to ai_conversations.
 * NOTE: does not extend BaseEntity because the table has
 * started_at/last_message_at instead of created_at/updated_at.
 */
@Entity
@Table(name = "ai_conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIConversation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "problem_id")
    private UUID problemId;

    @Column(name = "model_used", nullable = false, length = 100)
    private String modelUsed;

    @Enumerated(EnumType.STRING)
    @Column(name = "model_role", nullable = false, length = 20)
    private ModelRole modelRole;

    @CreationTimestamp
    @Column(name = "started_at", updatable = false, nullable = false)
    private Instant startedAt;

    @UpdateTimestamp
    @Column(name = "last_message_at", nullable = false)
    private Instant lastMessageAt;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sequence ASC")
    @Builder.Default
    private List<AIMessage> messages = new ArrayList<>();
}