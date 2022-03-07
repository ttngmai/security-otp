package com.example.securityotp.service;

import com.example.securityotp.entity.Resources;
import com.example.securityotp.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResourcesService {
    private final ResourceRepository resourceRepository;

    @Transactional
    public Resources getResources(long id) {
        return resourceRepository.findById(id).orElse(new Resources());
    }

    @Transactional
    public List<Resources> getResources() {
        return resourceRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Transactional
    public void createResources(Resources resources){
        resourceRepository.save(resources);
    }

    @Transactional
    public void deleteResources(long id) {
        resourceRepository.deleteById(id);
    }
}
