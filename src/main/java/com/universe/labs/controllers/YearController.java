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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universe.labs.counter.RequestCounter;
import com.universe.labs.counter.Synchr;
import com.universe.labs.entity.Year;
import com.universe.labs.exceptions.YearException;
import com.universe.labs.jpa.YearEntity;
import com.universe.labs.memory.YearMemory;
import com.universe.labs.service.YearDataBaseService;
import com.universe.labs.service.YearService;
import com.universe.labs.service.YearServiceAsync;
import com.universe.labs.service.YearValidation;

@RestController
@RequestMapping
public class YearController {

	@Autowired
	private YearService yearService;
	@Autowired
	private YearMemory yearMemory;
	@Autowired
	private YearValidation yearValidation;
	@Autowired
	private YearDataBaseService yearDataBase;
	@Autowired
	private YearServiceAsync yearServiceAsync;
	
	private static final Logger logger = LogManager.getLogger(YearController.class);
	
	@GetMapping("/year")
	public ResponseEntity<Year> infoAboutYear(@RequestParam("year") String Number)
	{
		try
		{
			Year year = new Year(yearValidation.checkParam(Number));
			logger.info("Created year");
			
			year.setDays(yearService.countDays(year));
			year.setLeap(yearService.isLeap(year));
			year.setHttpStatusCode(HttpStatus.OK);
			year.setErrorMessage("Success");
					
			Synchr.semaphore.release();
			RequestCounter.uincrement();
			yearMemory.saveYear(year);
			yearDataBase.saveYear(year);
			
			logger.info("Successfully GetMaping");
			return new ResponseEntity<Year>(year, HttpStatus.OK);
			
		}
		catch (YearException e)
		{
			logger.info("Cathed Exception");
			Year yearResponse = new Year();
			
			yearResponse.setErrorMessage(e.getReason());
			yearResponse.setHttpStatusCode(e.getStatusCode());
			
			logger.info("Created return");
			return new ResponseEntity<Year>(yearResponse, e.getStatusCode());
		}
	}
	
	@GetMapping("/year/async")
	public ResponseEntity<?> infoAboutYearAsyncCall(@RequestParam("year") String Number) {
	    logger.info("Start Async Call");
	    YearEntity yearEntity = yearDataBase.saveYear(0, true, 0);
	    try {
	        Year year = new Year(yearValidation.checkParam(Number));
	        logger.info("Created year");

	        yearServiceAsync.makeAsyncCall(year, yearEntity);

	        Map<String, Object> responseObject = new HashMap<>();
	        responseObject.put("message", "Start processing");
	        responseObject.put("programId", yearEntity.getId());

	        logger.info("Successfully GetMapping");
	        return ResponseEntity.ok(responseObject);
	    } catch (YearException e) {
	        logger.info("Caught Exception");
	        Year yearResponse = new Year();

	        yearResponse.setErrorMessage(e.getReason());
	        yearResponse.setHttpStatusCode(e.getStatusCode());

	        yearDataBase.deleteYearEntityById(yearEntity.getId());

	        logger.info("Created return");
	        return new ResponseEntity<Year>(yearResponse, e.getStatusCode());
	    }
	}
	
	@PostMapping("/year")
    public ResponseEntity<?> bulkYears(@RequestBody List<String> bodyList) {

        List<Year> resultList = new LinkedList<>();
        List<Year> notValidYears = new LinkedList<>();
        bodyList.forEach((a) -> {
            try {
                Year year = new Year(yearValidation.checkParam(a));
                year.setLeap(yearService.isLeap(year));
                year.setDays(yearService.countDays(year));
                year.setHttpStatusCode(HttpStatus.OK);
    			year.setErrorMessage("Success");
    			
    			Synchr.semaphore.release();
    			RequestCounter.uincrement();
    			yearMemory.saveYear(year);
    			
    			resultList.add(year);
    			
            } catch (YearException e) {
            	Year badYear = new Year();
            	
            	badYear.setErrorMessage(e.getReason());
    			badYear.setHttpStatusCode(e.getStatusCode());
    			
    			notValidYears.add(badYear);
                logger.error("Error in postMapping");
            }
        });
        
        yearDataBase.saveYears(resultList);
        logger.info("Successfully postMapping");
        int sumResult = yearService.calculateSumOfResult(resultList);
        int maxResult = yearService.findMaxOfResult(resultList);
        int minResult = yearService.findMinOfResult(resultList);
        
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("Years:", resultList);
        resMap.put("Not valid requests", notValidYears);
        resMap.put("Summary:", sumResult);
        resMap.put("Maximum value:", maxResult);
        resMap.put("Minimum value:", minResult);
        
        return new ResponseEntity<>(resMap, HttpStatus.CREATED);
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
}
