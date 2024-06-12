package com.grw.interval.service;

import com.grw.interval.dto.MovieDto;
import com.grw.interval.exception.MovieImportException;
import com.grw.interval.model.Movie;
import com.grw.interval.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final FileService fileService;

    private final MovieRepository movieRepository;

    public MovieService(FileService fileService, MovieRepository movieRepository) {
        this.fileService = fileService;
        this.movieRepository = movieRepository;
    }

    public List<Movie> importToDatabase() throws MovieImportException {
        List<MovieDto> movieDtos = fileService.getMoviesFromCsv();
        List<Movie> movies = new ArrayList<>();

        movieDtos.forEach(movieDto -> {
            //TODO - CHECK IF MOVIE/PRODUCER/STUDIO ALREADY EXISTS
            movies.add(movieRepository.save(movieDto.toModel()));
        });

        return movies;
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

    public Movie saveMovie(MovieDto movieDto) {
        return movieRepository.save(movieDto.toModel());
    }

}
