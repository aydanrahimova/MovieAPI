package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.dao.entity.Genre;
import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dao.entity.Person;
import com.example.internintelligence_movieapidevelopment.dao.repository.GenreRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.MovieRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.PersonRepository;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieResponseDto;
import com.example.internintelligence_movieapidevelopment.enums.PersonRole;
import com.example.internintelligence_movieapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_movieapidevelopment.exception.IllegalArgumentException;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFound;
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
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final PersonRepository personRepository;
    private final GenreRepository genreRepository;

    public MovieResponseDto getMovieById(Long id) {
        log.info("Try to find a movie with {} id",id);
        Movie movie = movieRepository.findById(id).orElseThrow(()->{
                log.error("Movie with {} is not found",id);
            return new ResourceNotFound("MOVIE_NOT_FOUND");
        });
        MovieResponseDto responseDto = movieMapper.toDto(movie);
        log.info("Movie with {} is found and returned",id);
        return responseDto;
    }

    public Page<MovieResponseDto> getMovies(int page,int size) {
        log.info("Try to get all movies");
        Pageable pageable = PageRequest.of(page,size);
        Page<Movie> movies = movieRepository.findAll(pageable);
        log.info("All movies are returned");
        return movies.map(movieMapper::toDto);
    }

//    public Page<MovieOverviewDto> getMoviesByGenre(String genreName,int page,int size){
//        log.info("Try to get all movies of {} genre",genreName);
//        Pageable pageable = PageRequest.of(page,size);
//        Page<Movie> movies = movieRepository.findByGenres_Name(genreName,pageable);
//        log.info("All movies of {} genre are returned",genreName);
//        return movies.map(movieMapper::toOverviewDto);
//    }

    public MovieResponseDto addMovie(MovieRequestDto movieRequestDto){
        if(movieRequestDto==null){
            log.warn("Can't add null movie");
            throw new IllegalArgumentException("Movie can't be null");
        }//lazimdi?
        if(movieRepository.existsByTitleAndReleaseDate(movieRequestDto.getTitle(),movieRequestDto.getReleaseDate())){
            log.warn("Movie with {} title name and {} release date is already exist",movieRequestDto.getTitle(),movieRequestDto.getReleaseDate());
            throw new AlreadyExistException("This movie is already exist");
        }
        Movie movie = movieMapper.toEntity(movieRequestDto);
        List<Person> cast = personRepository.findAllById(movieRequestDto.getPeopleId());
        List<Genre> genres = genreRepository.findAllById(movieRequestDto.getGenresId());
        movie.setPeople(cast);
        movie.setGenres(genres);
        if(movie.getPeople().stream().noneMatch(person -> person.getRole()== PersonRole.DIRECTOR)){
            log.warn("Movie with {} title name don't have a director",movieRequestDto.getTitle());
            throw new IllegalArgumentException("Movie without director can't exist");
        }
        movieRepository.save(movie);
        log.info("Movie with name {} is successfully added",movieRequestDto.getTitle());
        return movieMapper.toDto(movie);
    }

    public void deleteMovie(Long id){
        log.info("Attempting to delete a movie with {} id",id);
        if(!movieRepository.existsById(id)){
            log.error("Failed to delete movie. Movie '{}' doesn't exists.",id);
            throw  new ResourceNotFound("MOVIE_NOT_FOUND");
        }
        movieRepository.deleteById(id);
        log.info("Movie with {} id is successfully deleted",id);
    }

//    public MovieResponseDto updateMovie(Long id,MovieRequestDto movieRequestDto) {
//        log.info("Attempting to update a movie with ID '{}'",id);
//        Movie movie = movieRepository.findById(id).orElseThrow(()->{
//            log.error("Failed to update movie. Movie '{}' doesn't exists.",id);
//            return new ResourceNotFound("MOVIE_NOT_FOUND");
//        });
//        movieMapper.mapToUpdate(movie,movieRequestDto);
//        movieRepository.save(movie);
//        log.info("Movie is successfully updated");
//        return movieMapper.toDto(movie);
//    }//null ola bilmez deye iwlemir (databasede)
}
