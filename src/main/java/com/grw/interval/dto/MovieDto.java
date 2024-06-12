package com.grw.interval.dto;

import com.grw.interval.model.Movie;
import com.grw.interval.model.Producer;
import com.grw.interval.model.Studio;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class MovieDto {

    public MovieDto() {}

    public MovieDto(Integer year, String title, String studio, String producer, Boolean winner) {
        this.year = year;
        this.title = title;
        this.studio = studio;
        this.producer = producer;
        this.winner = winner;
    }

    private Integer year;
    private String title;
    private String studio;
    private String producer;
    private Boolean winner;

    public Movie toModel() {
        List<Studio> studios = Arrays.stream(studio.split(", ")).map(Studio::new).toList();
        List<Producer> producers = Arrays.stream(producer.contains(", ")
                ? producer.split(", ")
                : producer.split(" and ")).map(Producer::new).toList();
        return new Movie(year, title, studios, producers, winner);
    }

}
