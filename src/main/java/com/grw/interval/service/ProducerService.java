package com.grw.interval.service;

import com.grw.interval.dto.AwardIntervalsDto;
import com.grw.interval.repository.ProducerRepository;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public AwardIntervalsDto getAwardIntervals() {
        return null;
    }

}
