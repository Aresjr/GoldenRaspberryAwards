package com.grw.interval.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "producer")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true)
    String name;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "movie_producer",
      joinColumns = @JoinColumn(name = "producer_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
    List<Movie> movies;

    public Producer addMovie(Movie movie) {
        if (movies == null) {
            movies = List.of(movie);
        } else {
            movies.add(movie);
        }
        return this;
    }

    public Producer(String name) {
        this.name = name;
    }
}
