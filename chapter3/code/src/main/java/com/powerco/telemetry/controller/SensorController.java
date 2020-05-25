package com.powerco.telemetry.controller;

import com.powerco.telemetry.controller.dto.SensorDto;
import com.powerco.telemetry.domain.Location;
import com.powerco.telemetry.domain.Sensor;
import com.powerco.telemetry.service.TelemetryService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Salman.S
 *
 */
@RestController
@ApiResponse(description = "Sensor API")
@Slf4j
public class SensorController {

	@Autowired
	private TelemetryService telemetrySvc;

	@Autowired
	private Mapper mapper;

	// aggregate sensor root - Note the nested mapping for read operations
	@RequestMapping(value = "/locations/{locationId}/sensors", method = RequestMethod.GET)
	List<SensorDto> getAllSensorsForLocation(@PathVariable Long locationId) {
		List<Sensor> sensors = telemetrySvc.findAllSensors(locationId);
		return sensors
				.stream()
				.map(sensor -> mapper.convertToDto(sensor))
				.collect(Collectors.toList());
	}

	//@GetMapping("/locations/{locationId}/sensors/{sensorId}")
	@RequestMapping(value = "/locations/{locationId}/sensors/{sensorId}", method = RequestMethod.GET)
	SensorDto getSensorForLocation(@PathVariable Long locationId, @PathVariable Long sensorId) {
		return mapper.convertToDto(telemetrySvc.getOneSensor(locationId, sensorId));
	}

	// create a new sensor
	@PostMapping("/locations/{locationId}/sensors")
	SensorDto createSensor(@RequestBody SensorDto newSensorDto, @PathVariable Long locationId) {
		// log.info("### 1-New SensorDto: " + newSensorDto);
		Location location = telemetrySvc.getOneLocation(locationId);
		Sensor newSensor = mapper.convertToDomain(newSensorDto, location);
		// log.info("### 4- New Sensor: " + sensor);
		return mapper.convertToDto(telemetrySvc.createSensor(newSensor));
	}

	// Updates/Replace a sensor - Note the use of @RequestBody
	@PutMapping("/locations/{locationId}/sensors/{sensorId}")
	SensorDto updateSensor(@RequestBody SensorDto newSensorDto, @PathVariable Long locationId,
			@PathVariable Long sensorId) {

		// extract location using locationId
		Location location = telemetrySvc.getOneLocation(locationId);

		// update the sensor with the new values received from sensorDto
		Sensor updatedSensor = mapper.convertToDomain(newSensorDto, location);
		return mapper.convertToDto(telemetrySvc.updateSensor(updatedSensor));
	}

	// delete sensor
	@DeleteMapping("/locations/{locationId}/sensors/{sensorId}")
	void deleteSensor(@PathVariable Long locationId, @PathVariable Long sensorId) {
		telemetrySvc.deleteSensor(locationId, sensorId);
	}

}
