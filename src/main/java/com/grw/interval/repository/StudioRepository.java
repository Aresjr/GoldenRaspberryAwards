package com.grw.interval.repository;

import com.grw.interval.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Long> {

    Optional<Studio> findById(Long id);

    void deleteById(Long id);

    Optional<Studio> findByName(String name);
}
