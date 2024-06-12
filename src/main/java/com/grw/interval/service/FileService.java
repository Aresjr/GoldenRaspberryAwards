package com.grw.interval.service;

import com.grw.interval.model.Movie;
import com.grw.interval.model.Producer;
import com.grw.interval.model.Studio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

	@Value("${csv.file.path}")
	private String csvFilePath;

    private static final String[] MOVIE_HEADERS = {"year", "title", "studios", "producers", "winner"};

    private final static Character DELIMITER = ';';

    public List<Movie> getMoviesFromCsv() throws IOException {
        List<Movie> movies = new ArrayList<>();

        Reader in = Files.newBufferedReader(Paths.get(csvFilePath));
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setDelimiter(DELIMITER)
            .setHeader(MOVIE_HEADERS)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(in);

        System.out.println("START - PARSING CSV FILE");
        int count = 0;
        for (CSVRecord record : records) {
            movies.add(getMovieFromRecord(record));
            count++;
        }
        System.out.println("PARSED " + count + " MOVIES");
        System.out.println("END - PARSING CSV FILE");

        return movies;
    }

    private static Movie getMovieFromRecord(CSVRecord record) {
        Integer year = Integer.parseInt(record.get("year"));
        String title = record.get("title");
        List<Studio> studios = getStudiosFromRecord(record);
        List<Producer> producers = getProducersFromRecord(record);
        Boolean winner = "yes".equalsIgnoreCase(record.get("winner"));
        return new Movie(year, title, studios, producers, winner);
    }

    private static List<Studio> getStudiosFromRecord(CSVRecord record) {
        return Arrays.stream(record.get("studios").split(", ")).map(Studio::new).toList();
    }

    private static List<Producer> getProducersFromRecord(CSVRecord record) {
        String producers = record.get("producers");
        return Arrays.stream(producers.contains(", ")
                ? producers.split(", ")
                : producers.split(" and ")).map(Producer::new).toList();
    }

}
