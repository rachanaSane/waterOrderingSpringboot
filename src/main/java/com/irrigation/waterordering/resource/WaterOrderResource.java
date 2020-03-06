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
	
	/**
	 *  Create Water order for the farmers
	 * @param waterOrderDto
	 * @return
	 */
	@PostMapping("/order")	
	public WaterOrderDto createWaterOder(@Valid @RequestBody WaterOrderDto waterOrderDto) {
		return waterOrderService.createWaterOder(waterOrderDto);
	}

    /**
     * Get all the water orders
     * @return
     */
	@GetMapping("/orders")
	public List<WaterOrderDto> getWaterOder() {
		LOGGER.info("Get all the water orders for the user.");
		return waterOrderService.findAllOrders();
	}

	/**
	 * Get Specific water order for the Id requested
	 * @param orderId
	 * @return
	 */
	@GetMapping("/order/{orderId}")
	public WaterOrderDto getWaterOder(@PathVariable long orderId) {

		return waterOrderService.getWaterOder(orderId);

	}

	/**
	 * Cancel existing water order
	 * @param orderId
	 * @return
	 */
	@PatchMapping("/order/{orderId}")
	WaterOrderDto cancelWaterOrder(@PathVariable Long orderId) {
		return waterOrderService.cancelWaterOrder(orderId);
	}

	
}
