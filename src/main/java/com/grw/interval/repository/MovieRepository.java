package com.grw.interval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grw.interval.model.Movie;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findById(Long id);

}
