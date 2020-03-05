package com.irrigation.waterordering;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootTest
@ImportResource("application-context.xml")
class WaterOrderingApplicationTests {

	@Test
	void contextLoads() {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
	}

}
