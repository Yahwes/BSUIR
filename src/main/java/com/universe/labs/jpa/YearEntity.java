package com.universe.labs.jpa;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "Years")
public class YearEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "Number of year")
	private int numberOfYear;
	
	@Column(name = "Leap")
	private Boolean leap;
	
	@Column(name = "Days")
	private int days;
	
	public YearEntity(Integer id, int NumofYear, Boolean Leap, int Days)
	{
		this.id = id;
		this.numberOfYear=NumofYear;
		this.leap = Leap;
		this.days=Days;
	}

	public YearEntity(int NumofYear, Boolean Leap, int Days)
	{
		this.numberOfYear=NumofYear;
		this.leap = Leap;
		this.days=Days;
	}
	
	public YearEntity()
	{
		numberOfYear = 0;
		leap = false;
		days = 0;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setNumberOfYear(int NumofYear)
	{
		numberOfYear = NumofYear;
	}
	
	public void setLeap(Boolean leap)
	{
		this.leap = leap;
	}
	
	public void setDays(int days)
	{
		this.days = days;
	}
}
