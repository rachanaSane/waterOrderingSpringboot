package com.irrigation.waterordering.model;

public enum OrderStatus {
 REQUESTED("Order has been placed but not yet delivered"),
 INPROGRESS("Order is being delivered right now"),
 DELIVERED("Order has been delivered"),
 CANCELLED("Order was cancelled before delivery");
	
	private String description;
	
	private OrderStatus(String description) {
        this.description = description;
    }

	public String getDescription() {
		return this.description;
	}
	
	
}
