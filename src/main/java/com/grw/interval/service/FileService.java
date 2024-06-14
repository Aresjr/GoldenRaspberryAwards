package com.grw.interval.service;

import com.grw.interval.dto.MovieDto;
import com.grw.interval.dto.ProducerDto;
import com.grw.interval.dto.StudioDto;
import com.grw.interval.exception.MovieImportException;
import com.grw.interval.model.Movie;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
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

    private static final String[] MOVIE_HEADERS = {"year", "title", "studios", "producers", "winner"};

    private final static Character CSV_DELIMITER = ';';

    public List<MovieDto> getMoviesFromCsv(String csvFilePath) throws MovieImportException {
        List<MovieDto> movies = new ArrayList<>();
        Iterable<CSVRecord> records;

        try {
            Reader in = Files.newBufferedReader(Paths.get(csvFilePath));
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(CSV_DELIMITER)
                .setHeader(MOVIE_HEADERS)
                .setSkipHeaderRecord(true)
                .build();

            records = csvFormat.parse(in);
        } catch (IOException e) {
            throw new MovieImportException("Unable to read CSV file: " + csvFilePath);
        }

        System.out.println("START - PARSING CSV FILE");
        int countValid = 0;
        int countInvalid = 0;
        for (CSVRecord record : records) {
            MovieDto movieDto = getMovieFromRecord(record);
            if (movieDto != null) {
                movies.add(movieDto);
                countValid++;
            } else {
                countInvalid++;
            }
        }
        System.out.println("PARSED " + countValid + " VALID MOVIES");
        System.out.println("END - PARSING CSV FILE");

        return movies;
    }

    private static MovieDto getMovieFromRecord(CSVRecord record) throws MovieImportException {
        Integer year;
        Boolean winner;
        try {
            year = getYearFromRecord(record);
            winner = isWinnerFromRecord(record);
        } catch (MovieImportException e) {
            System.out.println("IGNORING RECORD: " + e.getMessage());
            return null;
        }

        List<StudioDto> studios = Arrays.stream(record.get("studios").split(", ")).map(StudioDto::new).toList();
        List<ProducerDto> producers =
                getProducersFromRecord(record.get("producers")).stream().map(ProducerDto::new).toList();

        return new MovieDto(year, record.get("title"), studios, producers, winner);
    }

    private static List<String> getProducersFromRecord(String producersRecord) {
        List<String> producers;
        if (producersRecord.contains(", ")) {
            producers = new ArrayList<>(Arrays.asList(producersRecord.split(", ")));

            String lastProducerName = producers.get(producers.size()-1);
            if (lastProducerName.startsWith("and ")) {
                producers.remove(producers.size()-1);
                producers.add(lastProducerName.replace("and ", ""));
            }

            if (lastProducerName.contains(" and ")) {
                producers.remove(producers.size()-1);
                producers.addAll(Arrays.stream(lastProducerName.split(" and ")).toList());
            }
        } else {
            producers = new ArrayList<>(Arrays.asList(producersRecord.split(" and ")));
        }
        return producers;
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
