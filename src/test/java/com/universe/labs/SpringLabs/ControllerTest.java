package com.universe.labs.SpringLabs;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.universe.labs.controllers.YearController;
import com.universe.labs.entity.Year;
import com.universe.labs.service.YearService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
	
	@Autowired
	public YearController yearController;
	
	@Mock
	private YearService yearService;
	
	@Test
	public void SuccessTest()
	{
		String Number = "12345";
		
		when(yearService.CheckLength(Number)).thenReturn(Boolean.FALSE);
		when(yearService.IsAlpha(Number)).thenReturn(Boolean.FALSE);
		when(yearService.IsNotValid(Integer.parseInt(Number))).thenReturn(Boolean.FALSE);
		Year year = new Year(Integer.parseInt(Number));
		
		when(yearService.CountDays(year)).thenReturn(365);
		when(yearService.IsLeap(year)).thenReturn(false);
	}
	
	@Test
	public void SuccessTest2()
	{
		String Number = "400";
		Year year = new Year(Integer.parseInt(Number));
		
		when(yearService.CountDays(year)).thenReturn(366);
		when(yearService.IsLeap(year)).thenReturn(true);
		Assert.assertNotNull(yearController.InfoAboutYear(Number));
	}
	
	@Test
	public void BadRequest()
	{
		String Number = "Boku wa yarichinbitchi no osu da yo osu da yo!!";
		
		when(yearService.CheckLength(Number)).thenReturn(Boolean.TRUE);
		when(yearService.IsAlpha(Number)).thenReturn(Boolean.TRUE);
		Assert.assertNotNull(yearController.InfoAboutYear(Number).getBody().getMessage());
	}
	
	@Test
	public void ServerError()
	{
		String Number = "-545";
		
		when(yearService.CheckLength(Number)).thenReturn(Boolean.FALSE);
		when(yearService.IsAlpha(Number)).thenReturn(Boolean.FALSE);
		when(yearService.IsNotValid(Integer.parseInt(Number))).thenReturn(Boolean.TRUE);
		Assert.assertNotNull(yearController.InfoAboutYear(Number).getBody().getMessage());
	}
	
	@Test
	public void GetTest()
	{
		yearController.getAllYears();
		yearController.getLeapYears();
		yearController.getNotLeapYears();
	}
}
