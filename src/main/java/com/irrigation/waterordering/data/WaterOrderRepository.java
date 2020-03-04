package com.irrigation.waterordering.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.irrigation.waterordering.model.WaterOrder;



@Repository
public interface WaterOrderRepository extends JpaRepository<WaterOrder, Long> {

}
