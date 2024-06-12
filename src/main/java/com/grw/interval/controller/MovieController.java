package com.grw.interval.controller;

import com.grw.interval.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MovieController {

	@Autowired
	MovieService movieService;

	@GetMapping("movie/read-file")
	public String index() throws IOException {
		return movieService.getMoviesFromCsv().toString();
	}

}
