package com.example.internintelligence_movieapidevelopment.service;

import com.example.internintelligence_movieapidevelopment.dao.entity.Movie;
import com.example.internintelligence_movieapidevelopment.dao.entity.Person;
import com.example.internintelligence_movieapidevelopment.dao.repository.MovieRepository;
import com.example.internintelligence_movieapidevelopment.dao.repository.PersonRepository;
import com.example.internintelligence_movieapidevelopment.dto.request.PersonRequestDto;
import com.example.internintelligence_movieapidevelopment.dto.response.MovieOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.PersonOverviewDto;
import com.example.internintelligence_movieapidevelopment.dto.response.PersonResponseDto;
import com.example.internintelligence_movieapidevelopment.exception.AlreadyExistException;
import com.example.internintelligence_movieapidevelopment.exception.ResourceNotFoundException;
import com.example.internintelligence_movieapidevelopment.mapper.MovieMapper;
import com.example.internintelligence_movieapidevelopment.mapper.PersonMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public PersonResponseDto getByID(Long id) {
        log.info("Attempting get person with ID {}", id);
        Person person = personRepository.findById(id).orElseThrow(() -> {
            log.error("Person with ID {} not found", id);
            return new ResourceNotFoundException("PERSON_NOT_FOUND");
        });
        log.info("Person found with ID: {}. Proceeding to retrieve movies.", id);
        List<Movie> movies = movieRepository.findMovieByCastId(id);
        List<MovieOverviewDto> movieOverview = movies.stream().map(movieMapper::toOverviewDto).toList();
        PersonResponseDto responseDto = personMapper.toDto(person);
        responseDto.setMovies(movieOverview);
        log.info("Successfully retrieved person with {} id", id);
        return responseDto;
    }

    public Page<PersonOverviewDto> getAll(Pageable pageable) {
        log.info("Attempting get all people.");
        Page<Person> people = personRepository.findAll(pageable);
        return people.map(personMapper::toOverviewDto);
    }

    public PersonResponseDto addPerson(PersonRequestDto requestDto) {
        log.info("Attempting add new person.");
        Person person = personMapper.toEntity(requestDto);
        if (personRepository.existsByFullNameIgnoreCaseAndBirthDate(person.getFullName(), person.getBirthDate())) {
            log.info("Person {} is already exist",requestDto.getFullName());
            throw new AlreadyExistException("PERSON_ALREADY_EXISTS");
        }
        log.info("Try to save new person.");
        personRepository.save(person);
        log.info("Person {} successfully saved", requestDto.getFullName());
        return personMapper.toDto(person);
    }

    public PersonResponseDto updateByID(Long id, PersonRequestDto requestDto) {
        log.info("Attempting update a person with {} id", id);
        Person person = personRepository.findById(id).orElseThrow(() -> {
            log.error("Failed to update: Person with ID {} not found.", id);
            return new ResourceNotFoundException("PERSON_NOT_FOUND");
        });

        log.info("Try to save an updated person");
        personMapper.mapForUpdate(person, requestDto);
        personRepository.save(person);

        log.info("Successfully updated person with ID: {}", id);

        return personMapper.toDto(person);
    }

    public void deleteByID(Long id) {
        log.info("Attempting delete a person with ID '{}'", id);
        if (!personRepository.existsById(id)) {
            log.error("Failed to delete: Person with ID {} not found.", id);
            throw new ResourceNotFoundException("PERSON_NOT_FOUND");
        }
        personRepository.deleteById(id);
        log.info("Successfully deleted person with ID: '{}'", id);
    }

}
