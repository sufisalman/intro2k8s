package com.powerco.telemetry.controller;

import com.powerco.telemetry.controller.dto.SensorDto;
import com.powerco.telemetry.domain.Location;
import com.powerco.telemetry.domain.Sensor;
import com.powerco.telemetry.service.TelemetryService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Salman.S
 *
 */
@RestController
@RequestMapping(value="/hal/locations/{locationId}")
@ApiResponse(description = "Sensor API")
@Slf4j
public class SensorControllerHal {

	@Autowired
	private TelemetryService telemetrySvc;

	@Autowired
	private Mapper mapper;

	@Autowired
	private SensorModelAssembler sensorModelAssembler;

	// aggregate sensor root - Note the nested mapping for read operations
	@RequestMapping(value = "/sensors", method = RequestMethod.GET)
	CollectionModel<EntityModel<SensorDto>> getAllSensorsForLocation(@PathVariable Long locationId) {
		List<Sensor> sensors = telemetrySvc.findAllSensors(locationId);
		List<SensorDto> sensorDtos = sensors.stream().map(sensor -> mapper.convertToDto(sensor))
				.collect(Collectors.toList());

		List<EntityModel<SensorDto>> sensorEntityModelList = sensorDtos.stream().map(sensorModelAssembler::toModel)
				.collect(Collectors.toList());

		return new CollectionModel<>(sensorEntityModelList,
				linkTo(methodOn(SensorControllerHal.class).getAllSensorsForLocation(locationId)).withSelfRel());
	}

	@RequestMapping(value = "/sensors/{sensorId}", method = RequestMethod.GET)
	ResponseEntity<EntityModel<SensorDto>> getSensorForLocation(@PathVariable Long locationId,
			@PathVariable Long sensorId) {

		SensorDto sensorDto;

		try {
			sensorDto = mapper.convertToDto(telemetrySvc.getOneSensor(locationId, sensorId));
		} catch (Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(sensorModelAssembler.toModel(sensorDto), HttpStatus.OK);
	}

	// create a new sensor
	@RequestMapping(value = "/sensors", method = RequestMethod.POST)
	ResponseEntity<EntityModel<SensorDto>> createSensor(@RequestBody SensorDto newSensorDto,
			@PathVariable Long locationId) {

		SensorDto sensorDto;

		try {
			// log.info("### 1-New SensorDto: " + newSensorDto);
			Location location = telemetrySvc.getOneLocation(locationId);
			Sensor newSensor = mapper.convertToDomain(newSensorDto, location);
			// log.info("### 4- New Sensor: " + sensor);
			sensorDto = mapper.convertToDto(telemetrySvc.createSensor(newSensor));
		} catch (Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(sensorModelAssembler.toModel(sensorDto), HttpStatus.CREATED);
	}

	// Updates/Replace a sensor - Note the use of @RequestBody
	@RequestMapping(value = "/sensors/{sensorId}", method = RequestMethod.PUT)
	ResponseEntity<EntityModel<SensorDto>> updateSensor(@RequestBody SensorDto newSensorDto,
			@PathVariable Long locationId, @PathVariable Long sensorId) {
		SensorDto sensorDto;
		try {
			// extract location using locationId
			Location location = telemetrySvc.getOneLocation(locationId);

			// update the sensor with the new values received from sensorDto
			Sensor updatedSensor = mapper.convertToDomain(newSensorDto, location);
			sensorDto = mapper.convertToDto(telemetrySvc.updateSensor(updatedSensor));

		} catch (Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(sensorModelAssembler.toModel(sensorDto), HttpStatus.NO_CONTENT);
	}

	// delete sensor
	@RequestMapping(value = "/sensors/{sensorId}", method = RequestMethod.DELETE)
	ResponseEntity<Void> deleteSensor(@PathVariable Long locationId, @PathVariable Long sensorId) {

		try {
			telemetrySvc.deleteSensor(locationId, sensorId);
		} catch (Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
