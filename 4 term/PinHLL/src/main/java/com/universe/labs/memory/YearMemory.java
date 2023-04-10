package com.universe.labs.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.universe.labs.entity.Year;

@Component
public class YearMemory {
	private Map<Integer, Year> dataStorage = new HashMap<Integer, Year>();
	
	public void saveYear(Year year)
	{
		dataStorage.put(year.getNumber(), year);
	}
	
	public Year getYear(Integer id)
	{
		return dataStorage.get(id);
	}
	
	public int getNumberYears()
	{
		return dataStorage.size();
	}
	
	public List<Year> getAllSavedYears()
	{
		List<Year> listYears = new ArrayList<Year>();
		dataStorage.forEach((a, b) -> listYears.add(b));
		return listYears;
 	}
	
	public List<Year> getAllSavedLeapYears()
	{
		List<Year> listYears = new ArrayList<Year>();
		dataStorage.forEach((a, b) -> {if(b.getLeap()) listYears.add(b);});
		return listYears;
 	}
	
	public List<Year> getAllSavedNotLeapYears()
	{
		List<Year> listYears = new ArrayList<Year>();
		dataStorage.forEach((a, b) -> {if(!b.getLeap()) listYears.add(b);});
		return listYears;
 	}
}
