package com.trading.mfanalyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.jai.test.db","com.trading.mfanalyser"})
public class MfanalyserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MfanalyserApplication.class, args);
	}

}
