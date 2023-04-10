package com.universe.labs.SpringLabs;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.universe.labs.entity.Year;
import com.universe.labs.memory.YearMemory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemoryTest {

	@Autowired
	YearMemory yearMemory;
	
	@Test
	public void SuccessMemoryTest()
	{
		Year year1 = new Year(105);
		year1.setLeap(false);
		year1.setDays(365);
		
		yearMemory.saveYear(year1);
		
		Assert.assertEquals(105, yearMemory.getYear(105).getNumber());
		
		Year year2 = new Year(400);
		year2.setLeap(true);
		year2.setDays(366);
		
		yearMemory.saveYear(year2);
		
		Assert.assertEquals(2, yearMemory.getNumberYears());
		
		Assert.assertEquals(1, yearMemory.getAllSavedLeapYears().size());
		Assert.assertEquals(1, yearMemory.getAllSavedNotLeapYears().size());
		Assert.assertEquals(2, yearMemory.getAllSavedYears().size());
	}
}
