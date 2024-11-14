package com.example.internintelligence_movieapidevelopment.mapper;

import com.example.internintelligence_movieapidevelopment.dao.entity.Person;
import com.example.internintelligence_movieapidevelopment.dto.request.PersonRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.PersonResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonRequestDto requestDto);
    PersonResponseDto toDto(Person person);
    void mapForUpdate(@MappingTarget Person person,PersonRequestDto requestDto);
}
