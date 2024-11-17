package com.example.internintelligence_movieapidevelopment.mapper;

import com.example.internintelligence_movieapidevelopment.dao.entity.Genre;
import com.example.internintelligence_movieapidevelopment.dto.request.GenreRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.GenreOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.GenreResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre toEntity(GenreRequestDto requestDto);

    @Mapping(source = "movies", target = "movies", ignore = true)
    GenreResponseDto toDto(Genre genre);

    GenreOverviewDto toOverviewDto(Genre genre);

    void mapForUpdate(@MappingTarget Genre genre, GenreRequestDto requestDto);
}
