package com.example.internintelligence_movieapidevelopment.controller;

import com.example.internintelligence_movieapidevelopment.dto.request.PersonRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.PersonOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.PersonResponseDto;
import com.example.internintelligence_movieapidevelopment.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/{id}")
    public PersonResponseDto getByID(@PathVariable Long id) {
        return personService.getByID(id);
    }

    @GetMapping("/get-all")
    public Page<PersonOverviewDto> getPeople(@PageableDefault Pageable pageable){
        return personService.getAll(pageable);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponseDto addPerson(@RequestBody @Valid PersonRequestDto requestDto) {
        return personService.addPerson(requestDto);
    }

    @PutMapping("/update/{id}")
    public PersonResponseDto updatePerson(@PathVariable Long id, @RequestBody PersonRequestDto requestDto) {
        return personService.updateByID(id, requestDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//204
    public void deletePersonByID(@PathVariable Long id) {
        personService.deleteByID(id);
    }

}
