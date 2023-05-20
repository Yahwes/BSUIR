package com.universe.labs.SpringLabs.ServiceTests;


import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.universe.labs.entity.Year;
import com.universe.labs.service.YearService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

		@Autowired
		public YearService yearService;
		
		@Test
		public void SuccessTestLeap()
		{
			Year year = new Year(400);
			Assert.assertEquals(366, (yearService.countDays(year)));
			Assert.assertEquals(true, yearService.isLeap(year));
		}

		@Test
		public void SuccessTestNotLeap()
		{
			Year year = new Year(501);
			Assert.assertEquals(365, (yearService.countDays(year)));
			Assert.assertEquals(false, yearService.isLeap(year));
		}
		
		@Test
		public void CalculateTest()
		{
			List<Year> resultList = new LinkedList<>();
			Year yearMin = new Year(500);
			Year yearMax = new Year(502);
			resultList.add(yearMax);
			resultList.add(yearMin);
			Assert.assertEquals(502, yearService.findMaxOfResult(resultList));
			Assert.assertEquals(500, yearService.findMinOfResult(resultList));
			Assert.assertEquals(1002, yearService.calculateSumOfResult(resultList));
		}
}
