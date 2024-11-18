package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.client.TmdbMovieResponse;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieFilterDto;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieResponseDto;
import com.example.internintelligence_movieapidevelopment.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    public MovieResponseDto getMovieByID(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/get-all")
    public Page<MovieOverviewDto> getMovies(
            @PageableDefault Pageable pageable,
            MovieFilterDto movieFilterDto
    ) {
        return movieService.getMovies(pageable, movieFilterDto);
    }

    @GetMapping("/popular")
    public TmdbMovieResponse getPopularMovies(@RequestParam int page) {
        return movieService.getPopularMovies(page);
    }

    @GetMapping("/top_rated")
    public TmdbMovieResponse getTopRatedMovies(@RequestParam int page){
        return movieService.getTopRatedMovies(page);
    }

    @GetMapping("/upcoming")
    public TmdbMovieResponse getUpcomingMovies(@RequestParam int page){
        return movieService.getUpcomingMovies(page);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponseDto addMovie(
            @Valid @RequestBody MovieRequestDto movieRequestDto
    ) {
        return movieService.addMovie(movieRequestDto);
    }

    @PutMapping("/update/{id}")
    public MovieResponseDto updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequestDto movieRequestDto
    ) {
        return movieService.updateMovie(id, movieRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

}
