package com.dsamastery.mastery.repository;

import com.dsamastery.mastery.entity.MasteryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MasteryRecordRepository extends JpaRepository<MasteryRecord, UUID> {
    
    Optional<MasteryRecord> findByEntityTypeAndEntityId(String entityType, UUID entityId);
    
    List<MasteryRecord> findByEntityType(String entityType);
    
    List<MasteryRecord> findByEntityTypeAndStatus(String entityType, String status);
}