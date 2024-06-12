package com.grw.interval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "studio")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    Movie movie;

    public Studio(String name) {
        this.name = name;
    }
}
