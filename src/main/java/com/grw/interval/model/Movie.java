package com.grw.interval.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String title;

    @Column(name = "_year")
    Integer year;

    @OneToMany(mappedBy="id")
    List<Studio> studios;

    @OneToMany(mappedBy="id")
    List<Producer> producers;

    Boolean winnerOfYear;

    public Movie(Integer year, String title, List<Studio> studios, List<Producer> producers, Boolean winner) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winnerOfYear = winner;
    }
}
