package com.irrigation.waterordering.resource;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.irrigation.waterordering.data.WaterOrderRepository;
import com.irrigation.waterordering.error.ErrorResponse;
import com.irrigation.waterordering.error.WaterOrderException;
import com.irrigation.waterordering.error.WaterOrderNotFoundException;
import com.irrigation.waterordering.model.OrderStatus;
import com.irrigation.waterordering.model.WaterOrder;





@RestController
public class WaterOrderResource {
	
	@Autowired
	private WaterOrderRepository waterOrderRepository;
	
	@GetMapping("/water/orders")
	public List<WaterOrder> getWaterOder() {		
		return waterOrderRepository.findAll();
	}
	
	@GetMapping("/water/order/{orderId}")
	public WaterOrder getWaterOder(@PathVariable long orderId) {		
		return waterOrderRepository.findById(orderId).map(x -> {
			OrderStatus status =OrderStatus.valueOf(x.getOrderStatus());
			x.setOrderStatus(status.getDescription());
			return x;
		})
				.orElseThrow(() -> new WaterOrderNotFoundException(orderId));
		
		
	}
	
	/*@DeleteMapping("/water/order/{orderId}")
	public ResponseEntity<Void> deleteTodo(@PathVariable long orderId){
		if(waterOrderRepository.findById(orderId).isPresent()) {
			WaterOrder waterOrder = waterOrderRepository.findById(orderId).get();
			if(waterOrder.getOrderStatus().equals(OrderStatus.DELIVERED.toString())) {
				throw new WaterOrderException("Order is already delivered");
			}else {
				waterOrderRepository.deleteById(orderId);
			}
		}	
		
		
		return ResponseEntity.notFound().build();
		
	}*/
	
	 // update author only
    @PutMapping("/water/order/{orderId}")
    WaterOrder cancelWaterOrder(@PathVariable Long orderId) {

        return waterOrderRepository.findById(orderId)
                .map(x -> {

                	if(OrderStatus.DELIVERED.toString().equals(x.getOrderStatus())) {
                		throw new WaterOrderException("Cancellation is not possible. Order is already delivered. ");
                	}else {
                		x.setOrderStatus(OrderStatus.DELIVERED.toString());
                		 return waterOrderRepository.save(x);
                	}

                })
                .orElseGet(() -> {
                    throw new WaterOrderNotFoundException(orderId);
                });

    }
	
	
		
	@PostMapping("/water/orders")
	public WaterOrder createTodo(@Valid @RequestBody WaterOrder waterOrder){
		
	
		return waterOrderRepository.save(waterOrder);
		
		
	}

}
