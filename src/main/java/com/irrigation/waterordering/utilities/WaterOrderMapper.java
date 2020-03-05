package com.irrigation.waterordering.utilities;

import com.irrigation.waterordering.error.WaterOrderException;
import com.irrigation.waterordering.model.WaterOrder;
import com.irrigation.waterordering.view.WaterOrderDto;

public class WaterOrderMapper implements ModelMapper {

	@Override
	public WaterOrder convertWaterOrderDtoToWaterOrder(WaterOrderDto waterOrderDto) {
		if(waterOrderDto ==null)
			throw new WaterOrderException("WaterOrder received to the service is null");
		
		WaterOrder order = new WaterOrder();
		
		order.setFarmName(waterOrderDto.getFarmName());
		order.setStartDateTime(waterOrderDto.getStartDateTime());
		
		return null;
	}

}
