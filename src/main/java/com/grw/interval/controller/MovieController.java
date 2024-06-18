package com.grw.interval.controller;

import com.grw.interval.dto.MovieDto;
import com.grw.interval.exception.MovieImportException;
import com.grw.interval.model.Movie;
import com.grw.interval.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MovieController {

	@Autowired
	MovieService movieService;

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("movie/import-from-csv")
	public ResponseEntity<String> importToDatabase() {
        try {
			List<Movie> importedMovies = movieService.importCsvToDatabase();
			String response = "Imported " + importedMovies.size() + " movies. List: " + importedMovies;
			return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (MovieImportException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("movies")
	public ResponseEntity<List<MovieDto>> getMovies() {
		List<MovieDto> response = movieService.getAllMovies()
				.stream().map(MovieDto::new).toList();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("movie/{id}")
	public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
		MovieDto response = movieService.getMovieById(id).map(MovieDto::new)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("movie/{id}")
	public ResponseEntity<String> deleteMovieById(@PathVariable Long id) {
		MovieDto response = movieService.getMovieById(id).map(MovieDto::new)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
		movieService.deleteMovieById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Movie deleted: " + response);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("movie")
	public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto movieDto) {
		Movie movie = movieService.save(movieDto);
		return ResponseEntity.status(HttpStatus.OK).body(new MovieDto(movie));
	}

}
