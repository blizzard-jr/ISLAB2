package org.example.is_lab1.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.is_lab1.models.dto.RingDTO;
import org.example.is_lab1.models.entity.Ring;
import org.example.is_lab1.repository.RingRepository;
import org.example.is_lab1.utils.RingMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RingService {
    private final RingRepository repository;
    private final RingMapper mapper;
    private final WebSocketNotificationService notificationService;


    public RingDTO create(RingDTO ring){
        Ring el = mapper.toEntity(ring);
        repository.save(el);
        RingDTO created = mapper.toDto(el);
        notificationService.notifyCreated(el.getId(), created);
        return created;
    }

    @Transactional
    public RingDTO modify(int id, RingDTO ring){
        Ring el = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ring not found with id " + id));
        mapper.updateEntityFromDto(ring, el);
        repository.save(el);
        RingDTO updated = mapper.toDto(el);
        notificationService.notifyUpdated(id, updated);
        return updated;
    }

    public void delete(int id){
        repository.deleteById(id);
        notificationService.notifyDeleted(id);
    }

    public Page<RingDTO> get(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findRingsWithoutOwner(pageable).map(mapper::toDto);
    }

    public RingDTO getById(int id){
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ring not found with id " + id)));
    }

}
