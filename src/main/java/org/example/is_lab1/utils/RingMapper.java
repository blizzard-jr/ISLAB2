package org.example.is_lab1.utils;

import org.example.is_lab1.models.dto.RingDTO;
import org.example.is_lab1.models.entity.Ring;
import org.example.is_lab1.repository.RingRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RingMapper {

    @Autowired
    protected RingRepository ringRepository;

    // Если передан только id - возвращаем ссылку, иначе создаем новый
    @Named("toEntity")
    public Ring toEntity(RingDTO dto) {
        if (dto == null) {
            return null;
        }

        // Если передан только id без остальных полей - используем ссылку на существующую сущность
        if (dto.id() != 0 && (dto.name() == null || dto.name().isBlank())) {
            return ringRepository.getReferenceById(dto.id());
        }

        // Иначе создаем новый объект через маппинг
        return mapToEntity(dto);
    }

    @Mapping(target = "id", ignore = true) // При создании нового id генерируется
    protected abstract Ring mapToEntity(RingDTO dto);

    public abstract RingDTO toDto(Ring entity);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntityFromDto(RingDTO dto, @MappingTarget Ring entity);
}
