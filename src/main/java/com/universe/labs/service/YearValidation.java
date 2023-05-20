package com.universe.labs.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.universe.labs.exceptions.YearException;

@Component
public class YearValidation {
	
	private static final Logger logger = LogManager.getLogger(YearValidation.class);
	
	public int checkParam(String Number)
	{
		if(checkLength(Number))
		{
			logger.error("Request is too big");
			throw new YearException(HttpStatus.INTERNAL_SERVER_ERROR, Number + " is too big");
		}
		
		if(isAlpha(Number))
		{
			logger.error("Number contains charachter");
			throw new YearException(HttpStatus.BAD_REQUEST, Number + " must contain only number");	
		}
		
		if(isNotValid(Integer.parseInt(Number)))
		{
			logger.error("Number less 0");
			throw new YearException(HttpStatus.BAD_REQUEST, Number + " must not be negative" );	
		}
		
		return Integer.parseInt(Number);
	}
	
	public Boolean checkLength(String num)
	{
		if(num.length() > 11)
			return true;
		return false;
	}
	
	public Boolean isAlpha(String num) {
		  return !(num.matches("-?\\d+(\\.\\d+)?"));  //match a number with optional '-' and decimal.
		}
	
	public Boolean isNotValid(int num)
	{
		return !(num > 0);
	}
}