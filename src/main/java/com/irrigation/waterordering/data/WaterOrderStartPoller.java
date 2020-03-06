package com.irrigation.waterordering.data;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.irrigation.waterordering.model.WaterOrder;
import com.irrigation.waterordering.resource.WaterOrderResource;

@Component
public class WaterOrderStartPoller {
	private static final Logger LOGGER=LoggerFactory.getLogger(WaterOrderStartPoller.class);

	public void handleJdbcMessage(List<Map<String, Object>> message) {
				
		for (Map<String, Object> resultMap: message) {
			//WaterOrder waterOrder = new WaterOrder();
		//	waterOrder.setId((Long) resultMap.get("ID"));
			//waterOrder.setFarmName((String) resultMap.get("FARM_NAME"));
			LOGGER.info("** Water order with id "+(Long) resultMap.get("ID") +" has started for farm "+(String) resultMap.get("FARM_NAME")+" **");
		}
	}
}
