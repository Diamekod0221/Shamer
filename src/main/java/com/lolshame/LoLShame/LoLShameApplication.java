package com.lolshame.LoLShame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.lolshame.LoLShame")
public class
LoLShameApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoLShameApplication.class, args);
	}

}
