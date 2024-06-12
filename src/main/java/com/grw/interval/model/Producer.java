package com.grw.interval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "producer")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    Movie movie;

    public Producer(String name) {
        this.name = name;
    }
}
