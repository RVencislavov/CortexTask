package com.example.CortexTask.mapper;


import com.example.CortexTask.persistence.dto.ExcursionDTO;
import com.example.CortexTask.persistence.entity.Excursion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExcursionMapper {
    ExcursionMapper INSTANCE = Mappers.getMapper(ExcursionMapper.class);
    ExcursionDTO toDto(Excursion excursion);

    Excursion toEntity(ExcursionDTO excursionDTO);
}
