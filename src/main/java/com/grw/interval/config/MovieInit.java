package com.grw.interval.config;

import com.grw.interval.exception.MovieImportException;
import com.grw.interval.service.MovieService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieInit {

    @Autowired
    private MovieService movieService;

    @PostConstruct
    public void initMovies()  {
        try {
            movieService.importCsvToDatabase();
        } catch (MovieImportException e) {
            System.out.println("NOT ABLE TO IMPORT MOVIES TO DATABASE: " + e.getMessage());
        }
    }

}
