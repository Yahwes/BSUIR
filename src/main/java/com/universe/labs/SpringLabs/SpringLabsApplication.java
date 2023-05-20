package com.universe.labs.SpringLabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.universe.labs", "com.universe.labs.jpa"})
@EnableJpaRepositories("com.universe.labs.jpa")
@EntityScan(basePackages = "com.universe.labs.jpa")
public class SpringLabsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLabsApplication.class, args);
	}

}
