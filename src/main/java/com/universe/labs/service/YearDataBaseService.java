package com.universe.labs.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universe.labs.entity.Year;
import com.universe.labs.jpa.YearRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.universe.labs.jpa.YearEntity;

@Service
public class YearDataBaseService {
	
	@Autowired
	private YearRepo yearRepo;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	private static final Logger logger = LogManager.getLogger(YearDataBaseService.class);
	
	public void saveYears(List<Year> years)
	{
		years.forEach(year -> saveYear(year));
		logger.info("Successfully saveYear");
	}
	
	public YearEntity saveYear(Year year)
	{
		YearEntity entity = new YearEntity(year.getNumber(),year.getLeap(),year.getDays());
		logger.info("Successfully saveYear " + year.getNumber());
		yearRepo.save(entity);
		return entity;
	}
	
	public YearEntity saveYear(int num, Boolean leap, int days)
	{
		YearEntity entity = new YearEntity(num, leap, days);
		logger.info("Successfully saveYear " + num);
		yearRepo.save(entity);
		return entity;
	}
	
	@Transactional
    public void updateYearEntity(Integer id, Year year) {
        YearEntity yearEntity = entityManager.find(YearEntity.class, id);
        
        if (yearEntity != null) {
            yearEntity.setNumberOfYear(year.getNumber());
            yearEntity.setLeap(year.getLeap());
            yearEntity.setDays(year.getDays());
        }
    }
	
	@Transactional
	public void deleteYearEntityById(Integer id) {
		YearEntity yearEntity = entityManager.find(YearEntity.class, id);
        if (yearEntity != null) {
            entityManager.remove(yearEntity);
        }
    }
}
