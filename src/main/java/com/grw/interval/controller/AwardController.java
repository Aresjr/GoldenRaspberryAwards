package com.grw.interval.controller;

import com.grw.interval.dto.AwardIntervalsDto;
import com.grw.interval.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AwardController {

	@Autowired
	ProducerService producerService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/award/intervals")
	public ResponseEntity<AwardIntervalsDto> getAwardIntervals() {
		return ResponseEntity.status(HttpStatus.OK).body(producerService.getAwardIntervals());
	}

}
