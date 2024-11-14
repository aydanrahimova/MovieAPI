package com.example.internintelligence_movieapidevelopment.mapper;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieResponseDto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MovieMapper {
    @Mapping(target = "peopleOverview", source = "cast")
    MovieResponseDto toDto(Movie movie);

    Movie toEntity(MovieRequestDto dto);

    MovieOverviewDto toOverviewDto(Movie movie);

    void mapForUpdate(@MappingTarget Movie movie, MovieRequestDto requestDto);
}
