package com.grw.interval.service;

import com.grw.interval.model.Movie;
import com.grw.interval.util.CsvUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MovieService {

	@Value("${csv.file.path}")
	private String csvFilePath;

    public List<Movie> getMoviesFromCsv() throws IOException {
        return CsvUtils.getMoviesFromFilePath(csvFilePath);
    }

    public List<Movie> importToDatabase() throws IOException {
        List<Movie> movies = getMoviesFromCsv();

        movies.forEach(movie -> {
            System.out.println(movie);
        });

        return movies;
    }

}
