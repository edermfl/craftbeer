package com.beerhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.beerhouse.beers.model")
@EnableJpaRepositories(basePackages = "com.beerhouse.beers.repository")
@SpringBootApplication(scanBasePackages = "com.beerhouse.beers")
@ComponentScan(basePackages = "com.beerhouse.beers")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}