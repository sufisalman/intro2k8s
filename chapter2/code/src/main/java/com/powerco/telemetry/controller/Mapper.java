package com.powerco.telemetry.controller;

import org.springframework.stereotype.Component;

import com.powerco.telemetry.controller.dto.LocationDto;
import com.powerco.telemetry.controller.dto.MeasurementDto;
import com.powerco.telemetry.controller.dto.SensorDto;
import com.powerco.telemetry.domain.Location;
import com.powerco.telemetry.domain.Measurement;
import com.powerco.telemetry.domain.Sensor;

@Component
public class Mapper {
	LocationDto convertToDto(Location location) {
		LocationDto locationDto = new LocationDto();
		locationDto.setId(location.getId());
		locationDto.setName(location.getName());
		locationDto.setZipcode(location.getZipcode());
		return locationDto;
	}

	Location convertToDomain(LocationDto locationDto) {
		Location location = new Location();
		location.setId(locationDto.getId());
		location.setName(locationDto.getName());
		location.setZipcode(locationDto.getZipcode());
		return location;
	}

	SensorDto convertToDto(Sensor sensor) {
		SensorDto sensorDto = new SensorDto();
		sensorDto.setId(sensor.getId());
		sensorDto.setName(sensor.getName());
		sensorDto.setLocationId(sensor.getLocation().getId());
		return sensorDto;
	}

	Sensor convertToDomain(SensorDto sensorDto, Location location) {
		Sensor sensor = new Sensor();
		sensor.setId(sensorDto.getId());
		sensor.setName(sensorDto.getName());
		sensor.setLocation(location);
		return sensor;
	}

	MeasurementDto convertToDto(Measurement measurement) {
		MeasurementDto measurementDto = new MeasurementDto();
		measurementDto.setId(measurement.getId());
		measurementDto.setValue(measurement.getValue());
		measurementDto.setTimestamp(measurement.getTimestamp());
		measurementDto.setSensorId(measurement.getSensor().getId());
		measurementDto.setLocationId(measurement.getSensor().getLocation().getId());
		return measurementDto;
	}

	Measurement convertToDomain(MeasurementDto measurementDto, Sensor sensor) {
		Measurement measurement = new Measurement();
		measurement.setId(measurementDto.getId());
		measurement.setValue(measurementDto.getValue());
		measurement.setTimestamp(measurementDto.getTimestamp());
		measurement.setSensor(sensor);
		return measurement;
	}
}
