package com.universe.labs.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universe.labs.counter.RequestCounter;

@RequestMapping
@RestController
public class RequestCounterController {
	Logger logger = LogManager.getLogger(RequestCounterController.class);
	
	@GetMapping("/counter")
	public ResponseEntity<List<String>> getCounter()
	{
		logger.info("Check RequestController");
		return ResponseEntity.ok(List.of("Synchronyzed " + String.valueOf(RequestCounter.getRequests()), "Not synchronized " + String.valueOf(RequestCounter.getURequests())));
	}
	
	@GetMapping("/counter/reset")
	public ResponseEntity<List<String>> resetCounter()
	{
		logger.info("Check RequestController ResetCounter");
		RequestCounter.zero();
		return ResponseEntity.ok(List.of("Synchronyzed " + String.valueOf(RequestCounter.getRequests()), "Not synchronized " + String.valueOf(RequestCounter.getURequests())));
	}
}
