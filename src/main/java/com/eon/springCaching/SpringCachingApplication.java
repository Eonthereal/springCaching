package com.eon.springCaching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.eon.springCaching.repository")
@EnableCaching
public class SpringCachingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCachingApplication.class, args);
	}


	//TODO: working example Ολες τις μεθοδους του Service, επιπλέον να κανω ενα working example με mongoDB, επίσης τη mongo να τη σηκώσω μέσα από το docker, εξτραδακι αν μπορώ να βάλω redis
}
