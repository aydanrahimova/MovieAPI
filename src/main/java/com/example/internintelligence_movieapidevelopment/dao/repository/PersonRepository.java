package com.example.internintelligence_movieapidevelopment.dao.repository;

import com.example.internintelligence_movieapidevelopment.dao.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

public interface PersonRepository extends JpaRepository<Person,Long> {
    Set<Person> findAllById(Long id);
    boolean existsByFullNameAndBirthDate(String fullName, LocalDate birthDate);
}
