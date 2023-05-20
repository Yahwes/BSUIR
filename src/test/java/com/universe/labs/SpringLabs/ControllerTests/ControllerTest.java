package com.universe.labs.SpringLabs.ControllerTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.universe.labs.controllers.YearController;
import com.universe.labs.entity.Year;
import com.universe.labs.exceptions.YearException;
import com.universe.labs.jpa.YearEntity;
import com.universe.labs.service.YearDataBaseService;
import com.universe.labs.service.YearService;
import com.universe.labs.service.YearServiceAsync;
import com.universe.labs.service.YearValidation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
	
	@Autowired
	public YearController yearController;
	
	@Mock
	private YearService yearService;
	
	@Mock
	private YearValidation yearValidation;
	
	@Mock
    private YearDataBaseService yearDataBase;

	@Mock
	private YearServiceAsync yearServiceAsync;
	
    @Captor
    private ArgumentCaptor<Year> yearCaptor;
	
	@Test
	public void SuccessTest()
	{
		String Number = "12345";
		
		when(yearValidation.checkLength(Number)).thenReturn(Boolean.FALSE);
		when(yearValidation.isAlpha(Number)).thenReturn(Boolean.FALSE);
		when(yearValidation.isNotValid(Integer.parseInt(Number))).thenReturn(Boolean.FALSE);
		Year year = new Year(Integer.parseInt(Number));
		
		when(yearService.countDays(year)).thenReturn(365);
		when(yearService.isLeap(year)).thenReturn(false);
	}
	
	@Test
	public void SuccessTest2()
	{
		String Number = "400";
		Year year = new Year(Integer.parseInt(Number));
		
		when(yearService.countDays(year)).thenReturn(366);
		when(yearService.isLeap(year)).thenReturn(true);
		Assert.assertNotNull(yearController.infoAboutYear(Number));
	}
	
	@Test
	public void BadRequest()
	{
		String Number = "Boku wa yarichinbitchi no osu da yo osu da yo!!";
		
		when(yearValidation.checkLength(Number)).thenReturn(Boolean.TRUE);
		when(yearValidation.isAlpha(Number)).thenReturn(Boolean.TRUE);
		Assert.assertNotNull(yearController.infoAboutYear(Number).getBody().getMessage());
	}
	
	@Test
	public void ServerError()
	{
		String Number = "-545";
		
		when(yearValidation.checkLength(Number)).thenReturn(Boolean.FALSE);
		when(yearValidation.isAlpha(Number)).thenReturn(Boolean.FALSE);
		when(yearValidation.isNotValid(Integer.parseInt(Number))).thenReturn(Boolean.TRUE);
		Assert.assertNotNull(yearController.infoAboutYear(Number).getBody().getMessage());
	}
	
	@Test
	public void GetTest()
	{
		yearController.getAllYears();
		yearController.getLeapYears();
		yearController.getNotLeapYears();
	}
	
	@Test
	public void BulkTest()
	{
		List<String> resultList = new LinkedList<>();
		resultList.add("500");
		resultList.add("-500");
		resultList.add("499");
		resultList.add("s");
		Assert.assertNotNull(yearController.bulkYears(resultList).getBody());
	}
	
	@Test
	public void infoAboutYearAsyncCall_ValidInput_ReturnsOkResponse() {
	    String number = "2023";
	    YearEntity yearEntity = new YearEntity(0, true, 0);
	    when(yearDataBase.saveYear(0, true, 0)).thenReturn(yearEntity);
	    when(yearValidation.checkParam(number)).thenReturn(2023);

	    ResponseEntity<?> response = yearController.infoAboutYearAsyncCall(number);

	    assertEquals(HttpStatus.OK, response.getStatusCode());
	}

    @Test
    void infoAboutYearAsyncCall_YearException_ReturnsErrorResponse() {
        String number = "invalid";
        YearEntity yearEntity = new YearEntity(0, true, 0);
        YearException exception = new YearException(HttpStatus.BAD_REQUEST, "Invalid year");
        when(yearDataBase.saveYear(0, true, 0)).thenReturn(yearEntity);
        when(yearValidation.checkParam(number)).thenThrow(exception);

        ResponseEntity<?> response = yearController.infoAboutYearAsyncCall(number);

        assertEquals(exception.getStatusCode(), response.getStatusCode());
    }
}

