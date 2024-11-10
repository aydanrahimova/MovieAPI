package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.MovieRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieResponseDto;
import com.example.internintelligence_movieapidevelopment.service.MovieService;
import com.example.internintelligence_movieapidevelopment.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return movieService.getMovies(page, size);
    }

//    @GetMapping("/by-genre/{genre}")
//    public Page<MovieOverviewDto> getMoviesByGenre(
//            @PathVariable String genre,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        return movieService.getMoviesByGenre(genre, page, size);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponseDto addMovie(@RequestBody @Validated(ValidationGroups.Create.class) MovieRequestDto movieRequestDto) {
        return movieService.addMovie(movieRequestDto);
    }

//    @PutMapping("/{id}")
//    public MovieResponseDto updateMovie(@PathVariable Long id,@RequestBody @Valid MovieRequestDto movieRequestDto){
//        return movieService.updateMovie(id,movieRequestDto);
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }


}
