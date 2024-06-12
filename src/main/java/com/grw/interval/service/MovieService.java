package com.grw.interval.service;

import com.grw.interval.dto.MovieDto;
import com.grw.interval.exception.MovieImportException;
import com.grw.interval.model.Movie;
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

    public MovieService(FileService fileService, MovieRepository movieRepository, ProducerRepository producerRepository, StudioRepository studioRepository) {
        this.fileService = fileService;
        this.movieRepository = movieRepository;
    }

    public List<Movie> importToDatabase() throws MovieImportException {
        List<MovieDto> movieDtos = fileService.getMoviesFromCsv();
        List<Movie> movies = new ArrayList<>();

        movieDtos.forEach(movieDto -> movies.add(save(movieDto)));

        return movies;
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie save(MovieDto movieDto) {
        return save(movieDto.toModel());
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

}
