package com.grw.interval.service;

import com.grw.interval.exception.MovieImportException;
import com.grw.interval.repository.MovieRepository;
import com.grw.interval.repository.ProducerRepository;
import com.grw.interval.repository.StudioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieServiceTest {

	@Autowired
	private MovieService movieService;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private StudioRepository studioRepository;

	@Autowired
	private ProducerRepository producerRepository;

	@BeforeEach
	void setUp() {
		movieRepository.deleteAll();
		studioRepository.deleteAll();
		producerRepository.deleteAll();
		movieService.setCsvFilePath("movielist.csv");
	}

	@Test
	void importCsvRecordsToDatabase() {
		Assertions.assertDoesNotThrow(() -> movieService.importCsvToDatabase());
		Assertions.assertEquals(206, movieRepository.count());
		Assertions.assertEquals(59, studioRepository.count());
		Assertions.assertEquals(359, producerRepository.count());
	}

	@Test
	void importInvalidPathCsvRecordsToDatabase() {
		movieService.setCsvFilePath("invalid-path.csv");
		MovieImportException exception = Assertions.assertThrows(MovieImportException.class,
				() -> movieService.importCsvToDatabase());
		Assertions.assertEquals("Unable to read CSV file: invalid-path.csv", exception.getMessage());

		Assertions.assertEquals(0, movieService.getAllMovies().size());
		Assertions.assertEquals(0, studioRepository.count());
		Assertions.assertEquals(0, producerRepository.count());
	}

	@Test
	void importCsvWithInvalidInputRecordsToDatabase() {
		movieService.setCsvFilePath("movielist-invalid.csv");
		Assertions.assertDoesNotThrow(() -> movieService.importCsvToDatabase());
		Assertions.assertEquals(14, movieRepository.count());
		Assertions.assertEquals(6, studioRepository.count());
		Assertions.assertEquals(14, producerRepository.count());
	}
}
