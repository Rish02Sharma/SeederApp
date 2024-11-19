package com.zemoso.seeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.zemoso.seeder"})
public class SeederApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeederApplication.class, args);
	}

}
