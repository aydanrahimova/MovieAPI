package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.dao.entity.Genre;
import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dao.repository.GenreRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.MovieRepository;
import com.example.internintelligence_movieapidevelopment.dto.request.GenreRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.GenreOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.GenreResponseDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieOverviewDto;
import com.example.internintelligence_movieapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFound;
import com.example.internintelligence_movieapidevelopment.mapper.GenreMapper;
import com.example.internintelligence_movieapidevelopment.mapper.MovieMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;


    public GenreResponseDto getByID(Long id, int page, int size) {
        log.info("Attempting to find genre with ID {}", id);
        Genre genre = genreRepository.findById(id).orElseThrow(() -> {
            log.error("Genre with ID {} not found", id);
            return new ResourceNotFound("GENRE_NOT_FOUND");
        });
        log.info("Genre found with ID: {}. Proceeding to retrieve movies.", id);
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieRepository.findMovieByGenres_Id(id, pageable);
        GenreResponseDto responseDto = genreMapper.toDto(genre);
        List<MovieOverviewDto> overviewDto = movies.stream().map(movieMapper::toOverviewDto).toList();
        responseDto.setMovies(overviewDto);
        return responseDto;
    }

    public List<GenreOverviewDto> getAll(){
        log.info("Attempting to get all genres");
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(genreMapper::toOverviewDto).toList();
    }

    public GenreResponseDto addGenre(GenreRequestDto requestDto) {
        log.info("Attempting to add new genre: {}", requestDto.getName());
        if (genreRepository.existsByName(requestDto.getName())) {
            log.error("Failed to add genre. Genre '{}' already exists.", requestDto.getName());
            throw new AlreadyExistException("GENRE_ALREADY_EXISTS");
        }
        Genre genre = genreMapper.toEntity(requestDto);
        genreRepository.save(genre);
        log.info("Genre '{}' successfully added.", requestDto.getName());
        return genreMapper.toDto(genre);
    }

    public void deleteByID(Long id){
        log.info("Attempting to delete genre with ID: {}", id);
        if (!genreRepository.existsById(id)) {
            log.error("Failed to delete genre. Genre '{}' doesn't exists.", id);
            throw new ResourceNotFound("GENRE_NOT_FOUND");
        }
        genreRepository.deleteById(id);
        log.info("Genre with ID '{}' successfully deleted",id);
    }
}
