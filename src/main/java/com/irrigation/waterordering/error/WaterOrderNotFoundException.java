package com.irrigation.waterordering.error;

public class WaterOrderNotFoundException extends RuntimeException {
	
	 public WaterOrderNotFoundException(Long id) {
	        super("Water order id not found : " + id);
	    }

}
