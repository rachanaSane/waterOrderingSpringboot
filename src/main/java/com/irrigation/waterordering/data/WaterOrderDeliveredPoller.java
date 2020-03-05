package com.irrigation.waterordering.data;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.irrigation.waterordering.model.WaterOrder;
import com.irrigation.waterordering.resource.WaterOrderResource;

@Component
public class WaterOrderDeliveredPoller {
	private static final Logger LOGGER=LoggerFactory.getLogger(WaterOrderDeliveredPoller.class);

	public void handleJdbcMessage(List<Map<String, Object>> message) {
		/*System.out.println("*************Inside JdbcMessageHandler****************************");
		for (Map<String, Object> resultMap: message) {
			System.out.println("Order placed with status requested..");
			for (String column: resultMap.keySet()) {
				System.out.println("column: " + column + " value: " + resultMap.get(column));
			}
			System.out.println("Updating order status to Delivered..");
			System.out.println("**************************************************************");
		}*/
		
		for (Map<String, Object> resultMap: message) {
			WaterOrder waterOrder = new WaterOrder();
			waterOrder.setId((Long) resultMap.get("ID"));
			waterOrder.setFarmName((String) resultMap.get("FARM_NAME"));
			LOGGER.info("** Water order with id "+waterOrder.getId() +" has been delivered for farm "+waterOrder.getFarmName()+" **");
		}
	}
}
