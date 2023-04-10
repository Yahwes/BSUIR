package com.universe.labs.SpringLabs;


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
			Assert.assertFalse(yearService.IsAlpha("400"));
			Assert.assertFalse(yearService.IsNotValid(400));
			Year year = new Year(400);
			Assert.assertEquals(366, (yearService.CountDays(year)));
			Assert.assertEquals(true, yearService.IsLeap(year));
		}
		

		@Test
		public void SuccessTestNotLeap()
		{
			Year year = new Year(501);
			Assert.assertEquals(365, (yearService.CountDays(year)));
			Assert.assertEquals(false, yearService.IsLeap(year));
		}
		
		@Test
		public void BadTestIsAlpha()
		{
			Assert.assertTrue(yearService.IsAlpha("Boku wa yarichinbitchi no osu da yo osu da yo!!"));
		}
		
		@Test
		public void BadTestIsNotValid()
		{
			Assert.assertTrue(yearService.IsNotValid(-400));
		}
		
		@Test
		public void BadCheckLength()
		{
			Assert.assertTrue(yearService.CheckLength("111111111111111111111111"));
		}
		
		@Test
		public void OkCheckLength()
		{
			Assert.assertFalse(yearService.CheckLength("111"));
		}
}
