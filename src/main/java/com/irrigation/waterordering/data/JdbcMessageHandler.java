package com.irrigation.waterordering.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class JdbcMessageHandler {

	public void handleJdbcMessage(List<Map<String, Object>> message) {
		System.out.println("*************Inside JdbcMessageHandler****************************");
		for (Map<String, Object> resultMap: message) {
			System.out.println("Order placed with status requested..");
			for (String column: resultMap.keySet()) {
				System.out.println("column: " + column + " value: " + resultMap.get(column));
			}
			System.out.println("Updating order status to Delivered..");
			System.out.println("**************************************************************");
		}
	}
}
