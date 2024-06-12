package com.grw.interval.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MovieInit {

    @Autowired
    private MovieService movieService;

    @PostConstruct
    public void initMovies()  {
        try {
            movieService.importToDatabase();
        } catch (IOException e) {
            System.out.println("NOT ABLE TO IMPORT MOVIES TO DATABASE");
            System.out.println(e.getLocalizedMessage());
        }
    }

}
