package com.universe.labs.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
//import java.util.LinkedList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universe.labs.counter.RequestCounter;
import com.universe.labs.counter.Synchr;
import com.universe.labs.entity.Year;
import com.universe.labs.exceptions.YearException;
import com.universe.labs.memory.YearMemory;
import com.universe.labs.service.YearService;

@RestController
@RequestMapping
public class YearController {

	@Autowired
	private YearService yearService;
	@Autowired
	private YearMemory yearMemory;
	private static final Logger logger = LogManager.getLogger(YearController.class);
	
	@GetMapping("/year")
	public ResponseEntity<Year> InfoAboutYear(@RequestParam("year") String Number)
	{
		try
		{
			Year year = new Year(CheckParam(Number));
			logger.info("Created year");
			
			year.setDays(yearService.CountDays(year));
			year.setLeap(yearService.IsLeap(year));
			year.setHttpStatusCode(HttpStatus.OK);
			year.setErrorMessage("Success");
					
			Synchr.semaphore.release();
			RequestCounter.Uincrement();
			yearMemory.saveYear(year);
			
			logger.info("Successfully GetMaping");
			return new ResponseEntity<Year>(year, HttpStatus.OK);
			
		}
		catch (YearException e)
		{
			logger.info("Cathed Exception");
			Year year = new Year();
			
			year.setErrorMessage(e.getReason());
			year.setHttpStatusCode(e.getStatusCode());
			
			logger.info("Created return");
			return new ResponseEntity<Year>(year, e.getStatusCode());
		}
	}
	
	@PostMapping("/year")
    public ResponseEntity<?> BulkYears(@RequestBody List<String> bodyList) {

        List<Year> resultList = new LinkedList<>();
        bodyList.forEach((a) -> {
            try {
                Year year = new Year(CheckParam(a));
                year.setLeap(yearService.IsLeap(year));
                year.setDays(yearService.CountDays(year));
                year.setHttpStatusCode(HttpStatus.OK);
    			year.setErrorMessage("Success");
    			resultList.add(year);
    			
            } catch (YearException e) {
                logger.error("Error in postMapping");
            }
        });
        
        logger.info("Successfully postMapping");
        int sumResult = yearService.calculateSumOfResult(resultList);
        int maxResult = yearService.findMaxOfResult(resultList);
        int minResult = yearService.findMinOfResult(resultList);
        
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("Years:", resultList);
        resMap.put("Summary:", sumResult);
        resMap.put("Maximum value:", maxResult);
        resMap.put("Minimum value:", minResult);
        
        return new ResponseEntity<>(resMap, HttpStatus.OK);
	}
	
	@GetMapping("/getYears")
	public ResponseEntity<List<Year>> getAllYears()
	{
		return ResponseEntity.ok(yearMemory.getAllSavedYears());
	}

	@GetMapping("/getYears/Leap")
	public ResponseEntity<List<Year>> getLeapYears()
	{
		return ResponseEntity.ok(yearMemory.getAllSavedLeapYears());
	}
	
	@GetMapping("/getYears/NotLeap")
	public ResponseEntity<List<Year>> getNotLeapYears()
	{
		return ResponseEntity.ok(yearMemory.getAllSavedNotLeapYears());
	}
	
	public int CheckParam(String Number)
	{
		if(yearService.CheckLength(Number))
		{
			logger.error("Request is too big");
			throw new YearException(HttpStatus.INTERNAL_SERVER_ERROR, "Request is too big");
		}
		
		if(yearService.IsAlpha(Number))
		{
			logger.error("Number contains charachter");
			throw new YearException(HttpStatus.BAD_REQUEST, "Number must contain only number");	
		}
		
		if(yearService.IsNotValid(Integer.parseInt(Number)))
		{
			logger.error("Number less 0");
			throw new YearException(HttpStatus.BAD_REQUEST, "Number must not be negative");	
		}
		
		return Integer.parseInt(Number);
	}
}
