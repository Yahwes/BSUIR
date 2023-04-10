package com.universe.labs.SpringLabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.universe.labs")
public class SpringLabsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLabsApplication.class, args);
	}

}
