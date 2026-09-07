package com.dsamastery.content.service;

import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.repository.AlgorithmRepository;
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
public class AlgorithmService {
    
    private final AlgorithmRepository algorithmRepository;
    
    public List<Algorithm> getPublishedAlgorithms() {
        return algorithmRepository.findByStatusOrderByNameAsc(ContentStatus.PUBLISHED);
    }
    
    public Optional<Algorithm> getAlgorithmBySlug(String slug) {
        return algorithmRepository.findBySlugWithRelations(slug);
    }
    
    public Optional<Algorithm> getAlgorithmById(UUID id) {
        return algorithmRepository.findById(id);
    }
}