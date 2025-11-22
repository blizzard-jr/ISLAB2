package org.example.is_lab1.utils;

import org.example.is_lab1.models.dto.BookCreatureDTO;
import org.example.is_lab1.models.entity.BookCreature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {MagicCityMapper.class, RingMapper.class})
public interface BookCreatureMapper {

    @Mapping(target = "creatureType", expression = "java(BookCreatureType.getType(dto.creatureType()))")
    @Mapping(target = "creatureLocation", qualifiedByName = "toEntity")
    @Mapping(target = "ring", qualifiedByName = "toEntity")
    BookCreature toEntity(BookCreatureDTO dto);
    BookCreatureDTO toDto(BookCreature entity);
    // Обновление существующей сущности (при modify)
    @Mapping(target = "id", ignore = true) // id не трогаем
    void updateEntityFromDto(BookCreatureDTO dto, @MappingTarget BookCreature entity);
}
