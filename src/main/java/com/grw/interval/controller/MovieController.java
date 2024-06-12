package com.grw.interval.controller;

import com.grw.interval.model.Movie;
import com.grw.interval.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
public class MovieController {

	@Autowired
	MovieService movieService;

	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("movie/import-from-csv")
	public List<Movie> importToDatabase() throws IOException {
		return movieService.importToDatabase();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("movie/{id}")
	public ResponseEntity<Movie> getMovieById(Long id) {
		Movie response = movieService.getMovieById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
