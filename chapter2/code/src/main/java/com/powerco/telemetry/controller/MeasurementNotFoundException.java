package com.powerco.telemetry.controller;

public class MeasurementNotFoundException extends RuntimeException {
	MeasurementNotFoundException(Long id){
		super("Could not find MEASUREMENT: " + id);
	}

}
