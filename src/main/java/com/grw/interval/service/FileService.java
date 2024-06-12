package com.grw.interval.service;

import com.grw.interval.dto.MovieDto;
import com.grw.interval.exception.MovieImportException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

	@Value("${csv.file.path}")
	private String csvFilePath;

    private static final String[] MOVIE_HEADERS = {"year", "title", "studios", "producers", "winner"};

    private final static Character DELIMITER = ';';

    public List<MovieDto> getMoviesFromCsv() throws MovieImportException {
        List<MovieDto> movies = new ArrayList<>();
        Iterable<CSVRecord> records;

        try {
            Reader in = Files.newBufferedReader(Paths.get(csvFilePath));
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(DELIMITER)
                .setHeader(MOVIE_HEADERS)
                .setSkipHeaderRecord(true)
                .build();

            records = csvFormat.parse(in);
        } catch (IOException e) {
            throw new MovieImportException("Unable to read CSV file: " + csvFilePath);
        }

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

    private static MovieDto getMovieFromRecord(CSVRecord record) throws MovieImportException {
        Integer year = getYearFromRecord(record);
        Boolean winner = isWinnerFromRecord(record);
        return new MovieDto(year, record.get("title"), record.get("producers"), record.get("producers"), winner);
    }

    private static Integer getYearFromRecord(CSVRecord record) throws MovieImportException {
        try {
            return Integer.parseInt(record.get("year"));
        } catch (NumberFormatException e) {
            throw new MovieImportException("Invalid year: " + record.get("year"));
        }
    }

    private static Boolean isWinnerFromRecord(CSVRecord record) throws MovieImportException {
        String winner = record.get("winner");
        if (!winner.isEmpty() && !winner.equals("yes")) {
            throw new MovieImportException("Invalid winner field: " + winner);
        }
        return "yes".equalsIgnoreCase(winner);
    }

}
