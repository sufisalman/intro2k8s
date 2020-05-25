package com.powerco.telemetry.domain;

import com.powerco.telemetry.repository.LocationRepository;
import com.powerco.telemetry.repository.MeasurementRepository;
import com.powerco.telemetry.repository.SensorRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class DataPreLoader{
	
	private static final String SENSOR_SECRET = "-secret";  

	//load locations
	@Bean
	CommandLineRunner initDatabase(LocationRepository locationRepo, SensorRepository sensorRepo, MeasurementRepository measurementRepo) {
	    return args -> {
	    
	      //create 2 new location
	      Location location1 = new Location("V3S1X1", "Fleetwood");
	      Location location2 = new Location("V4N5SX", "Walnut Grove");
	      
	      //add 2 sensors to location1 - Note the setter technique.	      
	      Sensor sensor1 = new Sensor ();
	      sensor1.setLocation(location1);
	      sensor1.setName("sensor1");
	      sensor1.setSecret("sensor1" + SENSOR_SECRET);
	      sensor1.setCreated(System.currentTimeMillis());
	      sensor1.setUpdated(System.currentTimeMillis());
	      
	      Sensor sensor2 = new Sensor();
	      sensor2.setLocation(location1);
	      sensor2.setName("sensor2");
	      sensor2.setSecret("sensor2" + SENSOR_SECRET);
	      sensor2.setCreated(System.currentTimeMillis());
	      sensor2.setUpdated(System.currentTimeMillis());
	      
	      //add measurements for sensor1	      
	      Measurement measurement1 = new Measurement();
	      measurement1.setSensor(sensor1);
	      measurement1.setTimestamp(new Date());
	      measurement1.setValue(100.10);
	      
	      Measurement measurement2 = new Measurement();
	      measurement2.setSensor(sensor1);
	      measurement2.setTimestamp(new Date());
	      measurement2.setValue(100.20);
	      
	      //add measurements for sensor2
	      Measurement measurement3 = new Measurement();
	      measurement3.setSensor(sensor2);
	      measurement3.setTimestamp(new Date());
	      measurement3.setValue(110.10);
	      
	      Measurement measurement4 = new Measurement();
	      measurement4.setSensor(sensor2);
	      measurement4.setTimestamp(new Date());
	      measurement4.setValue(110.20);
	      
	      //commit db transactions
	      log.info("Preloading Location: " + locationRepo.save(location1));
	      log.info("Preloading Location: " + locationRepo.save(location2));
	      log.info("Preloading Sensors: " + sensorRepo.save(sensor1));
	      log.info("Preloading Sensors: " + sensorRepo.save(sensor2));
	      log.info("Preloading Measurements: " + measurementRepo.save(measurement1));
	      log.info("Preloading Measurements: " + measurementRepo.save(measurement2));
	      log.info("Preloading Measurements: " + measurementRepo.save(measurement3));
	      log.info("Preloading Measurements: " + measurementRepo.save(measurement4));
	      
	    };
  	}
}
	
