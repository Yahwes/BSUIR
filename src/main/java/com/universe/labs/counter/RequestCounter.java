package com.universe.labs.counter;

public class RequestCounter {
	
	private static int count = -1;
	private static int Ucount = 0;
	
	public static void increment()
	{
		count++;
	}
	
	public static int getRequests()
	{
		return count;
	}
	
	public static void uincrement()
	{
		Ucount++;
	}
	
	public static int getURequests()
	{
		return Ucount;
	}
	
	public static void zero()
	{
		count = 0;
		Ucount = 0;
	}
}
