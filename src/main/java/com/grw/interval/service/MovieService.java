package com.grw.interval.service;

import com.grw.interval.dto.MovieDto;
import com.grw.interval.exception.MovieImportException;
import com.grw.interval.model.Movie;
import com.grw.interval.model.Producer;
import com.grw.interval.model.Studio;
import com.grw.interval.repository.MovieRepository;
import com.grw.interval.repository.ProducerRepository;
import com.grw.interval.repository.StudioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final FileService fileService;

    private final MovieRepository movieRepository;

    private final ProducerRepository producerRepository;

    private final StudioRepository studioRepository;

    public MovieService(FileService fileService, MovieRepository movieRepository, ProducerRepository producerRepository, StudioRepository studioRepository, ProducerRepository producerRepository1, StudioRepository studioRepository1) {
        this.fileService = fileService;
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository1;
        this.studioRepository = studioRepository1;
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie save(MovieDto movieDto) {
        Movie movie = save(movieDto.toModel());

        List<Producer> producers = movieDto.getProducers().stream()
            .map(producerDto -> {
                Producer producer = producerRepository.findByName(producerDto.getName())
                        .orElse(producerDto.toModel());
                if (producer.getMovies() == null) {
                    producer.setMovies(List.of(movie));
                } else {
                    producer.getMovies().add(movie);
                }
                return producerRepository.save(producer);
            }).toList();

        List<Studio> studios = movieDto.getStudios().stream()
            .map(studioDto -> {
                Studio studio = studioRepository.findByName(studioDto.getName())
                        .orElse(studioDto.toModel());
                if (studio.getMovies() == null) {
                    studio.setMovies(List.of(movie));
                } else {
                    studio.getMovies().add(movie);
                }
                return studioRepository.save(studio);
            }).toList();

        movie.setProducers(producers);
        movie.setStudios(studios);

        return movie;
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> importCsvToDatabase() throws MovieImportException {
        List<MovieDto> movieDtos = fileService.getMoviesFromCsv();

        List<Movie> movies = new ArrayList<>();
        movieDtos.forEach(movieDto -> movies.add(save(movieDto)));
        return movies;
    }

}
