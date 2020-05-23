package com.powerco.telemetry.controller;

import com.powerco.telemetry.service.TelemetryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import com.powerco.telemetry.domain.Location;
import com.powerco.telemetry.controller.dto.LocationDto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/***
 * 
 * @author Salman Design notes: Return HAL+Json formatted media.
 */
@Slf4j
@RestController
//apply class level mapping
@RequestMapping(value="/hal")
@ApiResponse(description = "Location API")

class LocationControllerHal {

	@Autowired
	private TelemetryService telemetrySvc;

	@Autowired
	private Mapper mapper;

	@Autowired
	private LocationModelAssembler locationModelAssembler;

	// Aggregate - location root - Returns HATEOEAS compliant response
	// Also note: @Relation(collectionRelation = "locations") in "LocationDto". This
	// is to change the display name in the _embedded {} resource
	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	ResponseEntity<CollectionModel<EntityModel<LocationDto>>> getAllLocations() {

		List<Location> locations;
		List<LocationDto> locationDtos;
		List<EntityModel<LocationDto>> locationEntityModelList;
		CollectionModel<EntityModel<LocationDto>> collectionModel;

		try {
			// get list of Locations from domain model.
			locations = telemetrySvc.getAllLocations();

			// Convert location list to locationDto list
			locationDtos = locations.stream().map(location -> mapper.convertToDto(location))
					.collect(Collectors.toList());

			// convert dto list into entity model list.
			locationEntityModelList = locationDtos.stream().map(locationModelAssembler::toModel)
					.collect(Collectors.toList());
		}

		catch (Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		// Package EntityModel list into a CollectionModel alongwith self-link
		collectionModel = new CollectionModel<>(locationEntityModelList,
				linkTo(methodOn(LocationControllerHal.class).getAllLocations()).withSelfRel());

		// wrap collection into ResponseEntity and return
		return new ResponseEntity<>(collectionModel, HttpStatus.OK);
	}

	// Single location
	@RequestMapping(value = "/locations/{id}", method = RequestMethod.GET)
	@Operation(description = "Retrieves a single location")
	ResponseEntity<EntityModel<LocationDto>> getOneLocation(@PathVariable Long id) {

		LocationDto locationDto;

		try {
			// Retrieve a Location domain model and convert it to a locationDto
			locationDto = mapper.convertToDto(telemetrySvc.getOneLocation(id));

		} catch (Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// convert dto to an entity model
		return new ResponseEntity<>(locationModelAssembler.toModel(locationDto), HttpStatus.OK);
	}

	// Create a new location. This is a POST
	@RequestMapping(value = "/locations", method = RequestMethod.POST)
	@Operation(description = "Create a new location")
	ResponseEntity<EntityModel<LocationDto>> createLocation(@RequestBody LocationDto newLocation) {

		LocationDto locationDto;

		try {
			Location location = mapper.convertToDomain(newLocation);
			locationDto = mapper.convertToDto(telemetrySvc.createLocation(location));
		}

		catch (Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(locationModelAssembler.toModel(locationDto), HttpStatus.CREATED);
	}

	// Delete a new location
	@RequestMapping(value = "/locations/{id}", method = RequestMethod.DELETE)
	@Operation(description = "Delete a location")
	ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
		try {
			telemetrySvc.deleteLocation(id);
		} catch (Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// Updates/Replace a location - Note the use of @RequestBody
	@RequestMapping(value = "/locations/{id}", method = RequestMethod.PUT)
	@Operation(description = "Update a location")
	ResponseEntity<EntityModel<LocationDto>> updateLocation(@RequestBody LocationDto newLocation,
			@PathVariable Long id) {

		LocationDto locationDto;

		try {

			Location location = mapper.convertToDomain(newLocation);
			locationDto = mapper.convertToDto(telemetrySvc.updateLocation(location, id));
		}

		catch (Exception ex) {
			log.info(ex.toString());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(locationModelAssembler.toModel(locationDto), HttpStatus.NO_CONTENT);
	}

}