package com.example.internintelligence_movieapidevelopment.mapper;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieResponseDto;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface MovieMapper {

//    @Mapping(target = "peopleOverview", source = "cast")
    MovieResponseDto toDto(Movie movie);

    Movie toEntity(MovieOverviewDto movieOverviewDto);

    Movie toEntity(MovieRequestDto movieRequestDto);

    Movie toEntity2(MovieResponseDto responseDto);

    MovieOverviewDto toOverviewDto(Movie movie);

    void mapForUpdate(@MappingTarget Movie movie, MovieRequestDto movieRequestDto);
}
