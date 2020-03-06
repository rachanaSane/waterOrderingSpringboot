package com.irrigation.waterordering.data;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.irrigation.waterordering.model.WaterOrder;



@Repository
public interface WaterOrderRepository extends JpaRepository<WaterOrder, Long> {
	
	
	@Query("SELECT order FROM WaterOrder order WHERE order.orderStatus not in('DELIVERED','CANCELLED') AND order.endDateTime >= :newStartDate AND order.startDateTime <= :newEndDate")
	Collection<WaterOrder> findAllOverlappedWaterOrders(@Param("newStartDate") LocalDateTime startDate, @Param("newEndDate") LocalDateTime endDate );

}
