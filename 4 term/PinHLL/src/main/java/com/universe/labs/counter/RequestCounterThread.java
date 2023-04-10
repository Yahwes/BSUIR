package com.universe.labs.counter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class RequestCounterThread extends Thread{

	Logger logger = LogManager.getLogger(RequestCounterThread.class);
	
	public RequestCounterThread()
	{
		super();
		start();
	}
	
	public void run()
	{
		while(Thread.currentThread().getName().equals("Thread-1"))
		{
			try
			{
				logger.info(Thread.currentThread().getName() + " waiting");
				Synchr.semaphore.acquire();
				RequestCounter.increment();
				logger.info("Successfuly increment, now counter: " + RequestCounter.getRequests());
			}
			catch(InterruptedException e)
			{
				logger.error("Interrupted");
			}
		}
	}
}
