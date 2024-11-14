package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.MovieFilterDto;
import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieResponseDto;
import com.example.internintelligence_movieapidevelopment.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    public MovieResponseDto getMovieByID(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping
    public Page<MovieResponseDto> getMovies(
            @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC) Pageable pageable,
            MovieFilterDto movieFilterDto
    ) {
        return movieService.getMovies(pageable, movieFilterDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponseDto addMovie(
            @RequestBody @Valid MovieRequestDto movieRequestDto
    ) {
        return movieService.addMovie(movieRequestDto);
    }

    @PutMapping("/{id}")
    public MovieResponseDto updateMovie(
            @PathVariable Long id,
            @RequestBody @Valid MovieRequestDto movieRequestDto
    ) {
        return movieService.updateMovie(id, movieRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

}
