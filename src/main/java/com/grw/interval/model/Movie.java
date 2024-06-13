package com.grw.interval.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "movie")
@ToString
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String title;

    @Column(name = "_year")
    Integer year;

    Boolean winner;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "movie_producer",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "producer_id"))
    List<Producer> producers;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "movie_studio",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "studio_id"))
    List<Studio> studios;

    public Movie(Integer year, String title, List<Studio> studios, List<Producer> producers, Boolean winner) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

}
