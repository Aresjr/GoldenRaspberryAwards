package com.grw.interval.service;

import com.grw.interval.dto.AwardIntervalsDto;
import com.grw.interval.dto.ProducerDto;
import com.grw.interval.dto.ProducerIntervalDto;
import com.grw.interval.model.Producer;
import com.grw.interval.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public Producer upsertProducer(ProducerDto producerDto) {
        return producerRepository.getOneByName(producerDto.getName())
                .orElseGet(() -> producerRepository.save(producerDto.toModel()));
    }

    public AwardIntervalsDto getAwardIntervals() {
        List<ProducerIntervalDto> winnerIntervals = producerRepository.findWinnerIntervals();

        if (winnerIntervals.isEmpty()) {
            return new AwardIntervalsDto(List.of(), List.of());
        }

        Integer minInterval = winnerIntervals.stream()
                .min(Comparator.comparingInt(ProducerIntervalDto::getWinInterval))
                .get().getWinInterval();
        List<ProducerIntervalDto> minIntervals = winnerIntervals.stream()
                .filter(producerIntervalDto -> Objects.equals(producerIntervalDto.getWinInterval(), minInterval))
                .toList();

        Integer maxInterval = winnerIntervals.stream()
                .max(Comparator.comparingInt(ProducerIntervalDto::getWinInterval))
                .get().getWinInterval();
        List<ProducerIntervalDto> maxIntervals = winnerIntervals.stream()
                .filter(producerIntervalDto -> Objects.equals(producerIntervalDto.getWinInterval(), maxInterval))
                .toList();

        return new AwardIntervalsDto(minIntervals, maxIntervals);
    }

}
