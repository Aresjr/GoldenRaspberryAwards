package com.grw.interval.service;

import com.grw.interval.model.Movie;
import com.grw.interval.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public List<Movie> importToDatabase() throws IOException {
        List<Movie> movies = fileService.getMoviesFromCsv();

        movies.forEach(movie -> {
            //TODO - CHECK IF MOVIE/PRODUCER/STUDIO ALREADY EXISTS
            movieRepository.save(movie);
        });

        return movies;
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

}
