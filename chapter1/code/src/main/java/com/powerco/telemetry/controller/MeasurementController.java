package com.powerco.telemetry.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.powerco.telemetry.controller.dto.MeasurementDto;
import com.powerco.telemetry.domain.Measurement;
import com.powerco.telemetry.domain.Sensor;
import com.powerco.telemetry.service.TelemetryService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@ApiResponse(description = "Measurement API")
public class MeasurementController {

	@Autowired
	private TelemetryService telemetrySvc;

	@Autowired
	private Mapper mapper;

	// aggregate Measurement root -
	@GetMapping("/locations/{locationId}/sensors/{sensorId}/measurements" )
	List<MeasurementDto> getAllMeasurementsForSensor(@PathVariable Long locationId, @PathVariable Long sensorId) {
		List<Measurement> measurements = telemetrySvc.getAllMeasurements(locationId, sensorId);
		return measurements
				.stream()
				.map(measurement -> mapper.convertToDto(measurement))
				.collect(Collectors.toList());
	}

	// get one measurement
	@GetMapping("locations/{locationId}/sensors/{sensorId}/measurements/{id}")
	MeasurementDto getOneMeasurement(@PathVariable Long locationId, @PathVariable Long sensorId,
			@PathVariable Long id) {
		return mapper.convertToDto(telemetrySvc.getOneMeasurement(locationId, sensorId, id));
	}

	// create a new Measurement
	@PostMapping("locations/{locationId}/sensors/{sensorId}/measurements")
	MeasurementDto createMeasurement(@RequestBody MeasurementDto newMeasurementDto, @PathVariable Long locationId,
			@PathVariable Long sensorId) {
		Sensor sensor = telemetrySvc.getOneSensor(locationId, sensorId);
		Measurement measurement = mapper.convertToDomain(newMeasurementDto, sensor);
		return mapper.convertToDto(telemetrySvc.createNewMeasurement(measurement));

	}

	// Updates/Replace a Measurement - Note the use of @RequestBody
	@PutMapping("locations/{locationId}/sensors/{sensorId}/measurements/{id}")
	MeasurementDto updateMeasurement(@RequestBody MeasurementDto newMeasurementDto, @PathVariable Long locationId,
			@PathVariable Long sensorId, @PathVariable Long id) {
		// sensor look up
		Sensor sensor = telemetrySvc.getOneSensor(locationId, sensorId);
		Measurement updatedMeasurement = mapper.convertToDomain(newMeasurementDto, sensor);
		return mapper.convertToDto(telemetrySvc.updateMeasurement(updatedMeasurement));
	}

	// delete Measurement
	@DeleteMapping("locations/{locationId}/sensors/{sensorId}/measurements/{id}")
	void deleteMeasurement(@PathVariable Long id) {
		telemetrySvc.deleteMeasurement(id);
	}

}
