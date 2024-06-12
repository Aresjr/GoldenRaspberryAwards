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
@Table(name = "studio")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "movie_studio",
      joinColumns = @JoinColumn(name = "studio_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
    List<Movie> movies;

    public Studio(String name) {
        this.name = name;
    }
}
