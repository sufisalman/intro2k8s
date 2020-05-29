package com.powerco.telemetry.controller;

import com.powerco.telemetry.service.TelemetryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import com.powerco.telemetry.domain.Location;
import com.powerco.telemetry.controller.dto.LocationDto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
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

/***
 * 
 * @author Salman
 * Design notes:
 */

@RestController
@ApiResponse(description = "Location API")

class LocationController {

	@Autowired
	private TelemetryService telemetrySvc;

	@Autowired
	private Mapper mapper;

	// Aggregate - location root
	// @GetMapping("/locations")
	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	List<LocationDto> getAllLocations() {
		List<Location> locations = telemetrySvc.getAllLocations();
		return locations.stream().map(location -> mapper.convertToDto(location)).collect(Collectors.toList());
	}

	// Single location
	// @GetMapping("/locations/{id}")
	@RequestMapping(value = "/locations/{id}", method = RequestMethod.GET)
	@Operation(description = "Retrieve a single location")

	LocationDto getOneLocation(@PathVariable Long id) {
		return mapper.convertToDto(telemetrySvc.getOneLocation(id));
	}

	// Create a new location. This is a POST
	// @PostMapping("/locations")
	@RequestMapping(value = "/locations", method = RequestMethod.POST)
	@Operation(description = "Create a new location")
	LocationDto createLocation(@RequestBody LocationDto newLocation) {
		Location location = mapper.convertToDomain(newLocation);
		return mapper.convertToDto(telemetrySvc.createLocation(location));
	}

	// Delete a new location
	// @DeleteMapping("/locations/{id}")
	@RequestMapping(value = "/locations/{id}", method = RequestMethod.DELETE)
	@Operation(description = "Delete a location")
	void deleteLocation(@PathVariable Long id) {
		telemetrySvc.deleteLocation(id);
	}

	// Updates/Replace a location - Note the use of @RequestBody
	// @PutMapping("/locations/{id}")
	@RequestMapping(value = "/locations/{id}", method = RequestMethod.PUT)
	@Operation(description = "Update a location")
	LocationDto updateLocation(@RequestBody LocationDto newLocation, @PathVariable Long id) {
		Location location = mapper.convertToDomain(newLocation);
		return mapper.convertToDto(telemetrySvc.updateLocation(location, id));
	}

}