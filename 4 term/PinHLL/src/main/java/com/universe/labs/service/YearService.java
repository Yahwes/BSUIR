package com.universe.labs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.universe.labs.entity.Year;

@Service
public class YearService {
	
	private static final Logger loger = LogManager.getLogger(Year.class);
	
	public YearService() {};
	
	public Boolean CheckLength(String num)
	{
		if(num.length() > 11)
			return true;
		return false;
	}
	
	public Boolean IsLeap(Year year)
	{
		int yearNumber = year.getNumber();
		loger.info("IsLeap");
		if(yearNumber % 4 == 0)
			return true;
		return false;
	}
	
	public int CountDays(Year year)
	{
		int yearNumber = year.getNumber();
		loger.info("Count Days");
		if(yearNumber % 4 == 0)
			return 366;
		return 365;
	}
	
	public Boolean IsAlpha(String num) {
		  return !(num.matches("-?\\d+(\\.\\d+)?"));  //match a number with optional '-' and decimal.
		}
	
	public Boolean IsNotValid(int num)
	{
		return !(num > 0);
	}
	
	 public int calculateSumOfResult(List<Year> resultList) {
	    int summary = 0;
	    if (!resultList.isEmpty()) {
	        summary = resultList.stream().map(year -> year.getNumber()).collect(Collectors.toList()).stream().mapToInt(Integer::intValue).sum();
	    }
	    return summary;
	 }

	 public int findMinOfResult(List<Year> resultList) {
        int min = 0;
        if (!resultList.isEmpty()) {
            min = resultList.stream().mapToInt(Year::getNumber).min().orElse(0);
        }
        return min;
	 }

	 public int findMaxOfResult(List<Year> resultList) {
    	int max = 0;
        if (!resultList.isEmpty()) {
            max = resultList.stream().mapToInt(Year::getNumber).max().orElse(0);
        }
        return max;
	 }
	
}
