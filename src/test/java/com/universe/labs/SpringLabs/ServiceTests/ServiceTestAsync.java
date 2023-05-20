package com.universe.labs.SpringLabs.ServiceTests;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.universe.labs.entity.Year;
import com.universe.labs.jpa.YearEntity;
import com.universe.labs.service.YearDataBaseService;
import com.universe.labs.service.YearService;
import com.universe.labs.service.YearServiceAsync;

import static org.mockito.Mockito.verify;

class ServiceTestAsync {
    
    @Mock
    private YearDataBaseService yearDataBase;
    
    @Mock
    private YearService yearService;
    
    @InjectMocks
    private YearServiceAsync yearServiceAsync;
    
    public ServiceTestAsync() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void makeAsyncCall_ShouldUpdateEntityWithYearData() {
        // Arrange
        Year year = new Year(2023);
        YearEntity entity = new YearEntity();
        
        // Act
        yearServiceAsync.makeAsyncCall(year, entity);
        
        // Assert
        verify(yearService).countDays(year);
        verify(yearService).isLeap(year);
        verify(yearDataBase).updateYearEntity(entity.getId(), year);
    }
}