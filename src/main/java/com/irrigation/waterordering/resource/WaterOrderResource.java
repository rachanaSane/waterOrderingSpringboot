package com.irrigation.waterordering.resource;


import java.util.List;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.irrigation.waterordering.service.WaterOrderService;
import com.irrigation.waterordering.view.WaterOrderDto;





@RestController
@RequestMapping(path = "/water")
public class WaterOrderResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaterOrderResource.class);

	
	@Autowired
	private WaterOrderService waterOrderService;

	@GetMapping("/orders")
	public List<WaterOrderDto> getWaterOder() {
		LOGGER.info("Get all the water orders for the user.");
		return waterOrderService.findAllOrders();
	}

	@GetMapping("/order/{orderId}")
	public WaterOrderDto getWaterOder(@PathVariable long orderId) {
		LOGGER.info("Get the water orders for id :" + orderId);

		return waterOrderService.getWaterOder(orderId);

	}

	
	@PatchMapping("/order/{orderId}")
	WaterOrderDto cancelWaterOrder(@PathVariable Long orderId) {
		return waterOrderService.cancelWaterOrder(orderId);
	}

	@PostMapping("/orders")
	public WaterOrderDto createWaterOder(@Valid @RequestBody WaterOrderDto waterOrderDto) {

		LOGGER.info("Create water order");
		return waterOrderService.createWaterOder(waterOrderDto);

	}

}
