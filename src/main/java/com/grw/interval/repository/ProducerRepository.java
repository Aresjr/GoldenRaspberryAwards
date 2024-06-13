package com.grw.interval.repository;

import com.grw.interval.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findById(Long id);

    void deleteById(Long id);

    Optional<Producer> findByName(String name);
}
