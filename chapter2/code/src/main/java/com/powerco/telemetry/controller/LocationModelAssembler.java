package com.powerco.telemetry.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.powerco.telemetry.controller.dto.LocationDto;

@Component
public class LocationModelAssembler implements RepresentationModelAssembler<LocationDto, EntityModel<LocationDto>>{

	@Override
	public EntityModel<LocationDto> toModel(LocationDto locationDto){
		EntityModel<LocationDto> entityModel = new EntityModel<>(locationDto, 
				linkTo(methodOn(LocationControllerHal.class).getOneLocation(locationDto.getId())).withSelfRel(),
				linkTo(methodOn(LocationControllerHal.class).getAllLocations()).withRel("locations"));
		//return HAL formatted response.
		return entityModel;	
	}	

}
