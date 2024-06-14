package com.grw.interval.service;

import com.grw.interval.dto.*;
import com.grw.interval.repository.MovieRepository;
import com.grw.interval.repository.ProducerRepository;
import com.grw.interval.repository.StudioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class ProducerServiceTest {

	@Autowired
	private ProducerService producerService;

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
	}

	@Test
	void checkMinAndMaxWinningIntervals() {
		Assertions.assertDoesNotThrow(() -> movieService.importCsvToDatabase());
		AwardIntervalsDto awardIntervalsDto = producerService.getAwardIntervals();
		Assertions.assertEquals(1, awardIntervalsDto.getMin().size());
		Assertions.assertEquals(1, awardIntervalsDto.getMax().size());

		ProducerIntervalDto minProducer = awardIntervalsDto.getMin().stream()
				.filter(p -> p.getProducer().equals("Joel Silver")).findFirst()
				.orElseThrow(() -> new AssertionError("Producer not found"));
		Assertions.assertEquals("Joel Silver", minProducer.getProducer());
		Assertions.assertEquals(1990, minProducer.getPreviousWin());
		Assertions.assertEquals(1991, minProducer.getFollowingWin());
		Assertions.assertEquals(1, minProducer.getWinInterval());

		ProducerIntervalDto maxProducer = awardIntervalsDto.getMax().stream()
				.filter(p -> p.getProducer().equals("Matthew Vaughn")).findFirst()
				.orElseThrow(() -> new AssertionError("Producer not found"));
		Assertions.assertEquals("Matthew Vaughn", maxProducer.getProducer());
		Assertions.assertEquals(2002, maxProducer.getPreviousWin());
		Assertions.assertEquals(2015, maxProducer.getFollowingWin());
		Assertions.assertEquals(13, maxProducer.getWinInterval());
	}

	@Test
	void checkMinAndMaxWinningIntervalsMultipleProducers() {
		Assertions.assertDoesNotThrow(() -> movieService.importCsvToDatabase());

		MovieDto movieDto = MovieDto.builder()
				.title("Can't Stop the Music 2").year(1981).winner(true)
				.producers(List.of(new ProducerDto("Allan Carr")))
				.studios(List.of(new StudioDto("Associated Film Distribution")))
				.build();
		movieService.save(movieDto);

		MovieDto movieDto2 = MovieDto.builder()
				.title("Mommie Dearest 2").year(1994).winner(true)
				.producers(List.of(new ProducerDto("Frank Yablans")))
				.studios(List.of(new StudioDto("Paramount Pictures")))
				.build();
		movieService.save(movieDto2);

		AwardIntervalsDto awardIntervalsDto = producerService.getAwardIntervals();
		Assertions.assertEquals(2, awardIntervalsDto.getMin().size());
		Assertions.assertEquals(2, awardIntervalsDto.getMax().size());

		ProducerIntervalDto minProducer = awardIntervalsDto.getMin().stream()
				.filter(p -> p.getProducer().equals("Allan Carr")).findFirst()
				.orElseThrow(() -> new AssertionError("Producer not found"));
		Assertions.assertEquals("Allan Carr", minProducer.getProducer());
		Assertions.assertEquals(1980, minProducer.getPreviousWin());
		Assertions.assertEquals(1981, minProducer.getFollowingWin());
		Assertions.assertEquals(1, minProducer.getWinInterval());

		ProducerIntervalDto minProducer2 = awardIntervalsDto.getMin().stream()
				.filter(p -> p.getProducer().equals("Joel Silver")).findFirst()
				.orElseThrow(() -> new AssertionError("Producer not found"));
		Assertions.assertEquals("Joel Silver", minProducer2.getProducer());
		Assertions.assertEquals(1990, minProducer2.getPreviousWin());
		Assertions.assertEquals(1991, minProducer2.getFollowingWin());
		Assertions.assertEquals(1, minProducer2.getWinInterval());

		ProducerIntervalDto maxProducer = awardIntervalsDto.getMax().stream()
				.filter(p -> p.getProducer().equals("Frank Yablans")).findFirst()
				.orElseThrow(() -> new AssertionError("Producer not found"));
		Assertions.assertEquals("Frank Yablans", maxProducer.getProducer());
		Assertions.assertEquals(1981, maxProducer.getPreviousWin());
		Assertions.assertEquals(1994, maxProducer.getFollowingWin());
		Assertions.assertEquals(13, maxProducer.getWinInterval());

		ProducerIntervalDto maxProducer2 = awardIntervalsDto.getMax().stream()
				.filter(p -> p.getProducer().equals("Matthew Vaughn")).findFirst()
				.orElseThrow(() -> new AssertionError("Producer not found"));
		Assertions.assertEquals("Matthew Vaughn", maxProducer2.getProducer());
		Assertions.assertEquals(2002, maxProducer2.getPreviousWin());
		Assertions.assertEquals(2015, maxProducer2.getFollowingWin());
		Assertions.assertEquals(13, maxProducer2.getWinInterval());
	}

}
