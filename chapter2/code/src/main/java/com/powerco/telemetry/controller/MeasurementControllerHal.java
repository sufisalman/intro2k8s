package com.powerco.telemetry.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.powerco.telemetry.controller.dto.MeasurementDto;
import com.powerco.telemetry.domain.Measurement;
import com.powerco.telemetry.domain.Sensor;
import com.powerco.telemetry.service.TelemetryService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/hal/locations/{locationId}/sensors/{sensorId}")
@ApiResponse(description = "Measurement API")
@Slf4j
public class MeasurementControllerHal {

	@Autowired
	private TelemetryService telemetrySvc;

	@Autowired
	private Mapper mapper;
	
	@Autowired
	private MeasurementModelAssembler measurementModelAssembler;

	// aggregate Measurement root -
	@RequestMapping(value = "/measurements", method = RequestMethod.GET)
	CollectionModel<EntityModel<MeasurementDto>> getAllMeasurementsForSensor(@PathVariable Long locationId, @PathVariable Long sensorId) {
		List<Measurement> measurements = telemetrySvc.getAllMeasurements(locationId, sensorId);
		List<MeasurementDto> measurementDtos = measurements.stream()
				.map(measurement -> mapper.convertToDto(measurement))
				.collect(Collectors.toList());
		List<EntityModel<MeasurementDto>> measurementEntityModelList = measurementDtos.stream()
				.map(measurementModelAssembler::toModel)
				.collect(Collectors.toList());
		
		return new CollectionModel<>(measurementEntityModelList, 
				linkTo(methodOn(MeasurementControllerHal.class).getAllMeasurementsForSensor(locationId, sensorId))
				.withSelfRel());
		
	}

	// get one measurement
	@RequestMapping(value = "/measurements/{id}", method = RequestMethod.GET)
	ResponseEntity<EntityModel<MeasurementDto>> getOneMeasurement(@PathVariable Long locationId, @PathVariable Long sensorId,
			@PathVariable Long id) {
		
		MeasurementDto measurementDto;
		try {
			measurementDto =  mapper.convertToDto(telemetrySvc.getOneMeasurement(locationId, sensorId, id));
			
		}
		catch(Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	
		return new ResponseEntity<>(measurementModelAssembler.toModel(measurementDto), HttpStatus.OK);
	}

	
	// create a new Measurement
	@RequestMapping(value = "/measurements", method = RequestMethod.POST)
	ResponseEntity<EntityModel<MeasurementDto>> createMeasurement(@RequestBody MeasurementDto newMeasurementDto, @PathVariable Long locationId,
			@PathVariable Long sensorId) {
		MeasurementDto measurementDto;
		try {
			Sensor sensor = telemetrySvc.getOneSensor(locationId, sensorId);
			Measurement measurement = mapper.convertToDomain(newMeasurementDto, sensor);
			measurementDto = mapper.convertToDto(telemetrySvc.createNewMeasurement(measurement));			
		}
		
		catch(Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(measurementModelAssembler.toModel(measurementDto), HttpStatus.CREATED);
	}

	// Updates/Replace a Measurement - Note the use of @RequestBody
	@RequestMapping(value = "/measurements/{id}", method = RequestMethod.PUT)
	ResponseEntity<EntityModel<MeasurementDto>> updateMeasurement(@RequestBody MeasurementDto newMeasurementDto, @PathVariable Long locationId,
			@PathVariable Long sensorId, @PathVariable Long id) {
		MeasurementDto measurementDto; 
		try {
			// sensor look up
			Sensor sensor = telemetrySvc.getOneSensor(locationId, sensorId);
			Measurement updatedMeasurement = mapper.convertToDomain(newMeasurementDto, sensor);
			measurementDto = mapper.convertToDto(telemetrySvc.updateMeasurement(updatedMeasurement));			
		}
		catch(Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}

		return new ResponseEntity<>(measurementModelAssembler.toModel(measurementDto), HttpStatus.NO_CONTENT);
	}
	
	// delete Measurement
	@RequestMapping(value = "/measurements/{id}", method = RequestMethod.DELETE)
	ResponseEntity<Void> deleteMeasurement(@PathVariable Long id) {
		try {
			telemetrySvc.deleteMeasurement(id);
		}
		catch(Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

}
