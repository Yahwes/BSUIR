package com.universe.labs.counter;

import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Component;

@Component
public class Synchr {
	public final static Semaphore semaphore = new Semaphore(1, Boolean.TRUE);	
}
