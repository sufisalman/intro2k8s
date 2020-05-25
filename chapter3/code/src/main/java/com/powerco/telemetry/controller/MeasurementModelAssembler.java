package com.powerco.telemetry.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.powerco.telemetry.controller.dto.MeasurementDto;

@Component
public class MeasurementModelAssembler implements RepresentationModelAssembler<MeasurementDto, EntityModel<MeasurementDto>>{

	@Override
	public EntityModel<MeasurementDto> toModel(MeasurementDto measurementDto){
		Long measurmentId = measurementDto.getId();
		Long sensorId = measurementDto.getSensorId();
		Long locationId = measurementDto.getLocationId();
		
		EntityModel<MeasurementDto> entityModel = new EntityModel<>(measurementDto, 
				linkTo(methodOn(MeasurementControllerHal.class).getOneMeasurement(locationId, sensorId, measurmentId))
				.withSelfRel(),
				linkTo(methodOn(MeasurementControllerHal.class).getAllMeasurementsForSensor(locationId, sensorId))
				.withRel("measurements"));
		//return HAL formatted response.
		return entityModel;	
	}	

}
