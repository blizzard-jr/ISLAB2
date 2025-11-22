package org.example.is_lab1.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.is_lab1.models.dto.BookCreatureDTO;
import org.example.is_lab1.models.entity.*;
import org.example.is_lab1.repository.CityRepository;
import org.example.is_lab1.repository.InteractRepository;
import org.example.is_lab1.repository.RingRepository;
import org.example.is_lab1.utils.BookCreatureMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InteractService {
    private final InteractRepository repository;
    private final BookCreatureMapper mapper;
    private final RingRepository ringRepository;
    private final CityRepository cityRepository;
    private final WebSocketNotificationService notificationService;


    public InteractService(InteractRepository repository, BookCreatureMapper mapper, RingRepository ringRepository, CityRepository cityRepository, WebSocketNotificationService notificationService) {
        this.repository = repository;
        this.mapper = mapper;
        this.ringRepository = ringRepository;
        this.cityRepository = cityRepository;
        this.notificationService = notificationService;
    }

    public BookCreatureDTO create(BookCreatureDTO creature){
        BookCreature el = mapper.toEntity(creature);
        repository.save(el);
        BookCreatureDTO created = mapper.toDto(el);
        notificationService.notifyCreated(el.getId(), created);
        return created;
    }

    @Transactional
    public BookCreatureDTO modify(int id, BookCreatureDTO creature){
        BookCreature existing =  repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Creature not found with id " + id));
        mapper.updateEntityFromDto(creature, existing);
        repository.save(existing);
        BookCreatureDTO updated = mapper.toDto(existing);
        notificationService.notifyUpdated(id, updated);
        return updated;
    }

    public void delete(int id){
        BookCreature creature = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Creature not found with id " + id));

        MagicCity city = creature.getCreatureLocation();

        // Удаляем существо
        repository.delete(creature);

        // Проверяем — остались ли ещё жители этого города
        if (city != null) {
            boolean hasResidents = repository.existsByCreatureLocation(city);
            if (!hasResidents) {
                cityRepository.delete(city);
            }
        }
        repository.deleteById(id);
        notificationService.notifyDeleted(id);
    }

    public Page<BookCreatureDTO> get(Pageable pageable, String filter){
        Page<BookCreature> page;
        if (filter != null && !filter.isEmpty()) {
            page  = repository.findByNameContainingIgnoreCase(filter, pageable);
        } else {
            page = repository.findAll(pageable);
        }

        return page.map(mapper::toDto);
    }

    public BookCreatureDTO getById(int id){
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Creature not found with id " + id)));
    }



}
