package com.irrigation.waterordering.resource;

import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import com.irrigation.waterordering.view.WaterOrderDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WaterOrderResourceTest {

	private static final Logger LOGGER=LoggerFactory.getLogger(WaterOrderResourceTest.class);
    // bind the above RANDOM_PORT
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void getWaterOders() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity(new URL("http://localhost:" + port + "/water/orders").toString(), String.class);
       // String expectedJson = "[{\"id\":10000200,\"farmName\":\"testfarm\",\"startDateTime\":\"2020-05-01 00:00:00\",\"duration\":\"01:00:00\",\"orderStatusDescription\":\"Order has been placed but not yet delivered\"}]";
        String expectedJson="[{\"id\":1,\"farmName\":\"rachFarm\",\"startDateTime\":\"2020-06-01 00:00:00\",\"duration\":\"02:00:00\",\"orderStatusDescription\":\"Order has been placed but not yet delivered\"},{\"id\":10000200,\"farmName\":\"testfarm\",\"startDateTime\":\"2020-05-01 00:00:00\",\"duration\":\"01:00:00\",\"orderStatusDescription\":\"Order has been placed but not yet delivered\"}]";
       // LOGGER.info("----->  : "+response.getBody());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);        
        
    }
    
    @Test
    @Order(2)
    public void testCreateWaterOrder() throws URISyntaxException 
    {
        final String baseUrl = "http://localhost:"+ port+"/water/order";
        URI uri = new URI(baseUrl);
        WaterOrderDto dto = new WaterOrderDto();
        dto.setFarmName("rachFarm");
        LocalDateTime date = LocalDateTime.parse("2020-06-01T00:00:00");
        dto.setStartDateTime(date);
        dto.setDuration("02:00:00");
         
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-Type", "application/json");      
 
        HttpEntity<WaterOrderDto> request = new HttpEntity<>(dto, headers);
         
        ResponseEntity<WaterOrderDto> result = restTemplate.postForEntity(uri, request, WaterOrderDto.class);
         
        System.out.println("done");
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }
    
    
    @Test
    @Order(3)
    public void getWaterOder() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity(new URL("http://localhost:" + port + "/water/order/10000200").toString(), String.class);
       // String expectedJson = "[{\"id\":10000200,\"farmName\":\"testfarm\",\"startDateTime\":\"2020-05-01 00:00:00\",\"duration\":\"01:00:00\",\"orderStatusDescription\":\"Order has been placed but not yet delivered\"}]";
      //  String expectedJson="[{\"id\":1,\"farmName\":\"rachFarm\",\"startDateTime\":\"2020-06-01 00:00:00\",\"duration\":\"02:00:00\",\"orderStatusDescription\":\"Order has been placed but not yet delivered\"},{\"id\":10000200,\"farmName\":\"testfarm\",\"startDateTime\":\"2020-05-01 00:00:00\",\"duration\":\"01:00:00\",\"orderStatusDescription\":\"Order has been placed but not yet delivered\"}]";
       String expectedJson="{\"id\":10000200,\"farmName\":\"testfarm\",\"startDateTime\":\"2020-05-01 00:00:00\",\"duration\":\"01:00:00\",\"orderStatusDescription\":\"Order has been placed but not yet delivered\"}";
        // LOGGER.info("----->  : "+response.getBody());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);        
        
    }
    
   
}
