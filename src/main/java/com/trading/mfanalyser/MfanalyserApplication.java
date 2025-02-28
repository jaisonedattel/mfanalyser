package com.trading.mfanalyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MfanalyserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MfanalyserApplication.class, args);
	}

}
