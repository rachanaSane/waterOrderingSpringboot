package com.irrigation.waterordering.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.irrigation.waterordering.model.WaterOrder;
import com.irrigation.waterordering.view.WaterOrderDto;

public interface WaterOrderService {
	WaterOrderDto createWaterOder(WaterOrderDto waterOrderDto);
	WaterOrderDto getWaterOder(long orderId);
	WaterOrderDto cancelWaterOrder(long orderId);
	List<WaterOrderDto> findAllOrders();
}
