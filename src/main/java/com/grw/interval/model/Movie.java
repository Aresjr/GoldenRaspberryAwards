package com.grw.interval.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "movie")
@ToString
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String title;

    @Column(name = "_year")
    Integer year;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    List<Studio> studios;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    List<Producer> producers;

    Boolean winner;

    public Movie(Integer year, String title, List<Studio> studios, List<Producer> producers, Boolean winner) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }
}
