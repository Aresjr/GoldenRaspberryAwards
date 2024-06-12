package com.grw.interval.dto;

import com.grw.interval.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MovieDto {

    Integer year;
    String title;
    List<StudioDto> studios;
    List<ProducerDto> producers;
    Boolean winner;

    public Movie toModel() {
        return new Movie(year, title,
                studios.stream().map(StudioDto::toModel).toList(),
                producers.stream().map(ProducerDto::toModel).toList(),
                winner);
    }

}
