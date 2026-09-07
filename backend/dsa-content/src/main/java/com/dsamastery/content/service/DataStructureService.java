package com.dsamastery.content.service;

import com.dsamastery.content.entity.DataStructure;
import com.dsamastery.content.repository.DataStructureRepository;
import com.dsamastery.common.enums.ContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataStructureService {
    
    private final DataStructureRepository dataStructureRepository;
    
    public List<DataStructure> getPublishedDataStructures() {
        return dataStructureRepository.findByStatusOrderByNameAsc(ContentStatus.PUBLISHED);
    }
    
    public Optional<DataStructure> getDataStructureBySlug(String slug) {
        return dataStructureRepository.findBySlugWithRelations(slug);
    }
    
    public Optional<DataStructure> getDataStructureById(UUID id) {
        return dataStructureRepository.findById(id);
    }
}