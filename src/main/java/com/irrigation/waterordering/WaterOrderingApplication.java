package com.irrigation.waterordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("application-context.xml")
public class WaterOrderingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterOrderingApplication.class, args);
	}

}
