package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.client.TmdbClient;
import com.example.internintelligence_movieapidevelopment.client.TmdbMovieResponse;
import com.example.internintelligence_movieapidevelopment.dao.entity.Genre;
import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dao.entity.Person;
import com.example.internintelligence_movieapidevelopment.dao.repository.GenreRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.MovieRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.PersonRepository;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieFilterDto;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieResponseDto;
import com.example.internintelligence_movieapidevelopment.enums.PersonRole;
import com.example.internintelligence_movieapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_movieapidevelopment.exception.IllegalArgumentException;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFound;
import com.example.internintelligence_movieapidevelopment.mapper.MovieMapper;
import com.example.internintelligence_movieapidevelopment.service.specification.MovieSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MovieService {


    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.default-language}")
    private String language;

    private final TmdbClient tmdbClient;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final PersonRepository personRepository;
    private final GenreRepository genreRepository;

    public MovieResponseDto getMovieById(Long id) {
        log.info("Attempting find a movie with ID '{}'", id);
        Movie movie = movieRepository.findById(id).orElseThrow(() -> {
            log.error("Movie with ID '{}' is not found", id);
            return new ResourceNotFound("MOVIE_NOT_FOUND");
        });
        MovieResponseDto responseDto = movieMapper.toDto(movie);
        log.info("Movie with ID '{}' is found and returned", id);
        return responseDto;
    }

    public Page<MovieOverviewDto> getMovies(Pageable pageable, MovieFilterDto movieFilterDto) {
        if (movieFilterDto.isEmpty()) {
            log.info("Attempting get all movies.");
            Page<Movie> movies = movieRepository.findAll(pageable);
            log.info("All movies are returned.");
            return movies.map(movieMapper::toOverviewDto);
        }

        log.info("Applying filters and fetching movies");

        Specification<Movie> specification = Specification.where(
                new MovieSpecification(movieFilterDto)
        );

        Page<Movie> movies = movieRepository.findAll(specification, pageable);

        log.info("Filtered movies are returned");
        return movies.map(movieMapper::toOverviewDto);
    }

    public TmdbMovieResponse getPopularMovies(int page){
        return tmdbClient.getPopularMovies(apiKey,language,page);
    }

    public TmdbMovieResponse getTopRatedMovies(int page) {
        return tmdbClient.getTopRatedMovies(apiKey,language,page);
    }

    public TmdbMovieResponse getUpcomingMovies(int page){
        return tmdbClient.getUpcomingMovies(apiKey,language,page);
    }


    public MovieResponseDto addMovie(MovieRequestDto movieRequestDto) {

        if (movieRepository.existsByTitleAndReleaseDate(movieRequestDto.getTitle(), movieRequestDto.getReleaseDate())) {
            log.warn("Movie with '{}' title name and '{}' release date is already exist", movieRequestDto.getTitle(), movieRequestDto.getReleaseDate());
            throw new AlreadyExistException("This movie already exists");
        }

        Movie movie = movieMapper.toEntity(movieRequestDto);

        Set<Person> cast = validateAndGetCast(movieRequestDto.getPeopleId());
        movie.setCast(cast);

        Set<Genre> genres = validateAndGetGenres(movieRequestDto.getGenresId());
        movie.setGenres(genres);

        movieRepository.save(movie);
        log.info("Movie with title '{}' was successfully added", movieRequestDto.getTitle());

        return movieMapper.toDto(movie);
    }

    public void deleteMovie(Long id) {
        log.info("Attempting delete a movie with ID '{}'", id);

        if (!movieRepository.existsById(id)) {
            log.error("Failed to delete movie. Movie ID '{}' doesn't exists.", id);
            throw new ResourceNotFound("MOVIE_NOT_FOUND");
        }

        movieRepository.deleteById(id);
        log.info("Movie with {} id is successfully deleted", id);
    }

    public MovieResponseDto updateMovie(Long id, MovieRequestDto movieRequestDto) {
        log.info("Attempting to update a movie with ID '{}'", id);

        Movie movie = movieRepository.findById(id).orElseThrow(() -> {
            log.error("Failed to update movie: Movie ID '{}' doesn't exists.", id);
            return new ResourceNotFound("MOVIE_NOT_FOUND");
        });
        movieMapper.mapForUpdate(movie, movieRequestDto);

        Set<Person> cast = validateAndGetCast(movieRequestDto.getPeopleId());
        movie.setCast(cast);

        Set<Genre> genres = validateAndGetGenres(movieRequestDto.getGenresId());
        movie.setGenres(genres);

        movieRepository.save(movie);
        log.info("Movie is successfully updated");
        return movieMapper.toDto(movie);
    }

    private Set<Person> validateAndGetCast(List<Long> peopleIds) {
        List<Person> cast = personRepository.findAllById(peopleIds);
        if (cast.size() != peopleIds.size()) {
            List<Long> invalidIds = peopleIds.stream()
                    .filter(personId -> cast.stream().noneMatch(person -> person.getId().equals(personId)))
                    .collect(Collectors.toList());
            log.error("Failed to process movie: Some people IDs are invalid: {}", invalidIds);
            throw new ResourceNotFound("PEOPLE_NOT_FOUND");
        }
        if (cast.stream().noneMatch(person -> person.getRole().equals(PersonRole.DIRECTOR))) {
            log.warn("Movie doesn't have a director");
            throw new IllegalArgumentException("Movie without director can't exist");
        }
        return new HashSet<>(cast);
    }

    private Set<Genre> validateAndGetGenres(List<Long> genreIds) {
        List<Genre> genres = genreRepository.findAllById(genreIds);
        if (genres.size() != genreIds.size()) {
            List<Long> invalidIds = genreIds.stream()
                    .filter(genreId -> genres.stream().noneMatch(genre -> genre.getId().equals(genreId)))
                    .collect(Collectors.toList());
            log.error("Failed to process movie: Some genre IDs are invalid: {}", invalidIds);
            throw new ResourceNotFound("GENRE_NOT_FOUND");
        }
        return new HashSet<>(genres);
    }


}
