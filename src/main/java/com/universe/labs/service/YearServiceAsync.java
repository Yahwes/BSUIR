package com.universe.labs.service;

import java.util.concurrent.CompletableFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.universe.labs.counter.RequestCounter;
import com.universe.labs.counter.Synchr;
import com.universe.labs.entity.Year;
import com.universe.labs.jpa.YearEntity;

@Service
public class YearServiceAsync {
    
    private static final Logger logger = LogManager.getLogger(YearValidation.class);
    
    @Autowired
    private YearDataBaseService yearDataBase;
    @Autowired
    private YearService yearService;
    
    @Async
    public void makeAsyncCall(Year year, YearEntity entity) {

    	CompletableFuture.runAsync(() -> {
            logger.info("Start Service Async Call");
            
            year.setDays(yearService.countDays(year));
            year.setLeap(yearService.isLeap(year));
            
            logger.info("Data was counted");
            
            entity.setNumberOfYear(year.getNumber());
            entity.setLeap(year.getLeap());
            entity.setDays(year.getDays());
            
            Synchr.semaphore.release();
            RequestCounter.uincrement();
            
            yearDataBase.updateYearEntity(entity.getId(), year);
            logger.info("Update entity");
        });
    }
}
