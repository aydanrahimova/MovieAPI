package com.example.internintelligence_movieapidevelopment.dao.entity;

import com.example.internintelligence_movieapidevelopment.enums.PersonRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private PersonRole role;
    private LocalDate birthDate;
    private String biography;
    @ManyToMany(mappedBy = "people")
    private List<Movie> movies;
}
