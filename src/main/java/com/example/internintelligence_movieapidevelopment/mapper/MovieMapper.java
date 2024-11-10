package com.example.internintelligence_movieapidevelopment.mapper;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(target = "peopleOverview", source = "people")
    MovieResponseDto toDto(Movie movie);
    Movie toEntity(MovieRequestDto dto);
    MovieOverviewDto toOverviewDto(Movie movie);
    void mapToUpdate(@MappingTarget Movie movie,MovieRequestDto requestDto);
}
