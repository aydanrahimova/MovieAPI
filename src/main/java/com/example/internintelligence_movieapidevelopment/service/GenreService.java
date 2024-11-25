package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.client.TmdbClient;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GenreService {
    @Value("${tmdb.api-key}")
    private String apiKey;

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final TmdbClient tmdbClient;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public List<GenreOverviewDto> fetchAllGenres(){
        List<GenreOverviewDto> genreOverview = tmdbClient.fetchAllGenres(apiKey).getGenres();
        List<Genre> genres = genreOverview.stream().map(genreMapper::toEntity2).toList();
        genreRepository.saveAll(genres);
        return genreOverview;
    }

    public GenreResponseDto getByID(Long id, Pageable pageable) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> {
            log.error("Genre with ID {} not found", id);
            return new ResourceNotFound("GENRE_NOT_FOUND");
        });

        log.info("Genre found with ID: {}. Proceeding to retrieve movies...", id);
        Page<Movie> moviesPage = movieRepository.findByGenresId(id, pageable);
        List<MovieOverviewDto> movieOverview = moviesPage.getContent().stream().map(movieMapper::toOverviewDto).toList();
        GenreResponseDto responseDto = genreMapper.toDto(genre);
        responseDto.setMovies(movieOverview);
        responseDto.setPageSize(moviesPage.getSize());
        responseDto.setTotalPages(moviesPage.getTotalPages());
        responseDto.setCurrentPage(moviesPage.getNumber());

        log.info("Returning genre response for Genre ID {} with {} movies.", id, moviesPage.getTotalElements());

        return responseDto;
    }

    public List<GenreOverviewDto> getAll() {
        log.info("Attempting to get all genres");
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(genreMapper::toOverviewDto).toList();
    }

    public GenreOverviewDto addGenre(GenreRequestDto requestDto) {
        log.info("Attempting add new genre: {}", requestDto.getName());

        if (genreRepository.existsByNameIgnoreCase(requestDto.getName())) {
            log.error("Failed to add genre. Genre '{}' already exists.", requestDto.getName());
            throw new AlreadyExistException("GENRE_ALREADY_EXISTS");
        }

        Genre genre = genreMapper.toEntity(requestDto);
        genreRepository.save(genre);

        log.info("Genre '{}' successfully added.", requestDto.getName());

        return genreMapper.toOverviewDto(genre);
    }

    public GenreOverviewDto editGenre(Long id, GenreRequestDto requestDto) {
        log.info("Attempting edit genre with ID: {}", id);
        Genre genre = genreRepository.findById(id).orElseThrow(() -> {
            log.error("Failed to update genre. Genre ID '{}' doesn't exist", id);
            return new ResourceNotFound("GENRE_NOT_FOUND");
        });
        genreMapper.mapForUpdate(genre, requestDto);
        genreRepository.save(genre);
        return genreMapper.toOverviewDto(genre);
    }

    public void deleteByID(Long id) {
        log.info("Attempting to delete genre with ID: {}", id);

        if (!genreRepository.existsById(id)) {
            log.error("Failed to delete genre. Genre '{}' doesn't exists.", id);
            throw new ResourceNotFound("GENRE_NOT_FOUND");
        }

        genreRepository.deleteById(id);
        log.info("Genre with ID '{}' successfully deleted", id);
    }

}
