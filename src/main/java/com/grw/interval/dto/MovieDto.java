package com.grw.interval.dto;

import com.grw.interval.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class MovieDto {

    Integer year;

    String title;

    List<StudioDto> studios;

    List<ProducerDto> producers;

    Boolean winner;

    public Movie toModel() {
        return new Movie(year, title, List.of(), List.of(), winner);
    }

    public MovieDto(Movie movie) {
        this.year = movie.getYear();
        this.title = movie.getTitle();
        this.studios = movie.getStudios().stream()
                .map(studioDto -> new StudioDto(studioDto.getName())).toList();
        this.producers = movie.getProducers().stream()
                .map(producerDto -> new ProducerDto(producerDto.getName())).toList();
        this.winner = movie.getWinner();
    }

}
