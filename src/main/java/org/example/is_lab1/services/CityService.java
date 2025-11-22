package org.example.is_lab1.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.is_lab1.models.dto.MagicCityDTO;
import org.example.is_lab1.models.entity.MagicCity;
import org.example.is_lab1.repository.CityRepository;
import org.example.is_lab1.utils.MagicCityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository repository;
    private final MagicCityMapper mapper;
    private final WebSocketNotificationService notificationService;


    public MagicCityDTO create(MagicCityDTO city){
        MagicCity el = mapper.toEntity(city);
        repository.save(el);
        MagicCityDTO created = mapper.toDto(el);
        notificationService.notifyCreated(el.getId(), created);
        return created;
    }

    @Transactional
    public MagicCityDTO modify(int id, MagicCityDTO city){
        MagicCity el = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found with id " + id));
        mapper.updateEntityFromDto(city, el);
        repository.save(el);
        MagicCityDTO updated = mapper.toDto(el);
        notificationService.notifyUpdated(id, updated);
        return updated;
    }

    public void delete(int id){
        repository.deleteById(id);
        notificationService.notifyDeleted(id);
    }

    public Page<MagicCityDTO> get(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public MagicCityDTO getById(int id){
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("City not found with id " + id)));
    }
}
