package com.powerco.telemetry.controller;

public class SensorNotFoundException extends RuntimeException {
	SensorNotFoundException(Long id){
		super("Could not find SENSOR: " + id);
	}

}
