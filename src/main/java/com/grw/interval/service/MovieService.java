package com.grw.interval.service;

import com.grw.interval.dto.MovieDto;
import com.grw.interval.dto.StudioDto;
import com.grw.interval.exception.MovieImportException;
import com.grw.interval.model.Movie;
import com.grw.interval.model.Producer;
import com.grw.interval.model.Studio;
import com.grw.interval.repository.MovieRepository;
import com.grw.interval.repository.StudioRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Setter
    @Value("${csv.file.path}")
	private String csvFilePath;

    private final FileService fileService;

    private final MovieRepository movieRepository;

    private final ProducerService producerService;

    private final StudioService studioService;

    public MovieService(FileService fileService, MovieRepository movieRepository, ProducerService producerService,
                        StudioService studioService) {
        this.fileService = fileService;
        this.movieRepository = movieRepository;
        this.producerService = producerService;
        this.studioService = studioService;
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie save(MovieDto movieDto) {
        Movie movie = save(movieDto.toModel());

        List<Producer> producers = movieDto.getProducers().stream()
            .map(producerDto -> producerService.upsertProducer(producerDto).addMovie(movie)).toList();

        List<Studio> studios = movieDto.getStudios().stream()
            .map(studioDto -> studioService.upsertStudio(studioDto).addMovie(movie)).toList();

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
        List<MovieDto> movieDtos = fileService.getMoviesFromCsv(csvFilePath);

        List<Movie> movies = new ArrayList<>();
        movieDtos.forEach(movieDto -> movies.add(save(movieDto)));
        return movies;
    }

}
