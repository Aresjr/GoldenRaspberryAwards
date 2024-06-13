package com.grw.interval.service;

import com.grw.interval.dto.AwardIntervalsDto;
import com.grw.interval.dto.ProducerIntervalDto;
import com.grw.interval.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public AwardIntervalsDto getAwardIntervals() {

        List<Object[]> winnerIntervals = producerRepository.findWinnerIntervals();
        List<ProducerIntervalDto> minIntervals = winnerIntervals.stream()
                .map(ProducerIntervalDto::fromQueryObject)
                .min(Comparator.comparingInt(ProducerIntervalDto::getInterval))
                .stream().toList();

        List<ProducerIntervalDto> maxIntervals = winnerIntervals.stream()
                .map(ProducerIntervalDto::fromQueryObject)
                .max(Comparator.comparingInt(ProducerIntervalDto::getInterval))
                .stream().toList();

        return new AwardIntervalsDto(minIntervals, maxIntervals);
    }

}
