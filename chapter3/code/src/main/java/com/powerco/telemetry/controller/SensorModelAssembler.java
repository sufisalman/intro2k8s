package com.powerco.telemetry.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.powerco.telemetry.controller.dto.SensorDto;

@Component
public class SensorModelAssembler implements RepresentationModelAssembler<SensorDto, EntityModel<SensorDto>>{

	@Override
	public EntityModel<SensorDto> toModel(SensorDto sensorDto){
		EntityModel<SensorDto> entityModel = new EntityModel<>(sensorDto, 
				linkTo(methodOn(SensorControllerHal.class)
						.getSensorForLocation(sensorDto.getLocationId(), sensorDto.getId())).withSelfRel(),
				linkTo(methodOn(SensorControllerHal.class).getAllSensorsForLocation(sensorDto.getLocationId())).withRel("sensors"));
		//return HAL formatted response.
		return entityModel;	
	}	

}
