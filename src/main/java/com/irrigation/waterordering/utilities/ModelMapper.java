package com.irrigation.waterordering.utilities;

import com.irrigation.waterordering.model.WaterOrder;
import com.irrigation.waterordering.view.WaterOrderDto;

public interface ModelMapper {
	
	WaterOrder convertWaterOrderDtoToWaterOrder(WaterOrderDto waterOrderDto);

}
