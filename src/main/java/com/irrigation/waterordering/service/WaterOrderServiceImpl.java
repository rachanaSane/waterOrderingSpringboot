package com.irrigation.waterordering.service;

import com.irrigation.waterordering.data.WaterOrderRepository;
import com.irrigation.waterordering.error.WaterOrderException;
import com.irrigation.waterordering.error.WaterOrderNotFoundException;
import com.irrigation.waterordering.model.OrderStatus;
import com.irrigation.waterordering.model.WaterOrder;
import com.irrigation.waterordering.resource.WaterOrderResource;
import com.irrigation.waterordering.view.WaterOrderDto;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WaterOrderServiceImpl implements WaterOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaterOrderServiceImpl.class);


	@Autowired
	private WaterOrderRepository waterOrderRepository;

	@Override
	public WaterOrderDto createWaterOder(WaterOrderDto waterOrderDto) {
		
		WaterOrder waterOrder = convertToEntity(waterOrderDto);
		validateTimeOverlap(waterOrder);
		waterOrder.setOrderStatus(OrderStatus.REQUESTED.toString());
		WaterOrder createdWaterOrder = waterOrderRepository.save(waterOrder);
		LOGGER.info("New order has been placed for farm "+createdWaterOrder.getFarmName() + " with farmId:"+createdWaterOrder.getId());
		return convertToDto(createdWaterOrder);

	}
	
	@Override
	public WaterOrderDto getWaterOder(long orderId) {
		return waterOrderRepository.findById(orderId).map(x -> {
			return convertToDto(x);

		}).orElseThrow(() -> new WaterOrderNotFoundException(orderId));
	}
	
	@Override
	public WaterOrderDto cancelWaterOrder(long orderId) {
		
		return waterOrderRepository.findById(orderId)
        .map(x -> {  return performCancelOrder(x); })
        .orElseGet(() -> {
            throw new WaterOrderNotFoundException(orderId);
        });

		
	}
	
	@Override
	public List<WaterOrderDto> findAllOrders() {
		// TODO Auto-generated method stub
		return waterOrderRepository.findAll().stream().map(x -> {
			return convertToDto(x);
		}).collect(Collectors.toList());
		
	}


	private WaterOrderDto performCancelOrder(WaterOrder waterOrder) {
		if(OrderStatus.DELIVERED.toString().equals(waterOrder.getOrderStatus())) {
    		throw new WaterOrderException("Cancellation is not possible. Order is already delivered. ");
    	}else {
    		waterOrder.setOrderStatus(OrderStatus.CANCELLED.toString());
    		WaterOrder updatedWaterOrder  = waterOrderRepository.save(waterOrder);
    		LOGGER.info("Water order has been cancelled for id : "+waterOrder.getId());
    		 return convertToDto(updatedWaterOrder);
    	}
	}

	private WaterOrderDto convertToDto(WaterOrder waterOrder) {
		
		WaterOrderDto waterOrderDto = new WaterOrderDto();
		waterOrderDto.setId(waterOrder.getId());
		waterOrderDto.setFarmName(waterOrder.getFarmName());
		waterOrderDto.setStartDateTime(waterOrder.getStartDateTime());
		waterOrderDto.setDuration(convertToDuration(waterOrder.getDuration()));
		waterOrderDto.setOrderStatusDescription(OrderStatus.valueOf(waterOrder.getOrderStatus()).getDescription());

		return waterOrderDto;

	}

	private WaterOrder convertToEntity(WaterOrderDto waterOrderDto) {
		WaterOrder waterOrder = new WaterOrder();		

		waterOrder.setFarmName(waterOrderDto.getFarmName());
		waterOrder.setStartDateTime(waterOrderDto.getStartDateTime());
		
		// convert string duration received to miliseconds
		waterOrder.setDuration(convertToMiliseconds(waterOrderDto.getDuration()));

		// calculate end date and add to start date
		waterOrder.setEndDateTime(waterOrderDto.getStartDateTime().plus(waterOrder.getDuration(), ChronoUnit.MILLIS));

		return waterOrder;
	}

	private void validateTimeOverlap(WaterOrder waterOrder) {
		Collection<WaterOrder> orders = waterOrderRepository.findAllOverlappedWaterOrders(waterOrder.getStartDateTime(),
				waterOrder.getEndDateTime());
		if (!orders.isEmpty()) {
			throw new WaterOrderException(
					"Other Water order is in progress during this time. Please choose another duration");
		}
	}

	private long convertToMiliseconds(String duration) {
		LOGGER.info("String duration received :" + duration);

		String[] tokens = duration.split(":");
		int secondsToMs = Integer.parseInt(tokens[2]) * 1000;
		int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
		int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
		long totalMs = secondsToMs + minutesToMs + hoursToMs;
		LOGGER.info("duration converted to miliseconds :" + totalMs);
		return totalMs;

	}

	private String convertToDuration(long duration) {
		return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(duration),
				TimeUnit.MILLISECONDS.toMinutes(duration)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
				TimeUnit.MILLISECONDS.toSeconds(duration)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
	
	}

	

	
	
}
