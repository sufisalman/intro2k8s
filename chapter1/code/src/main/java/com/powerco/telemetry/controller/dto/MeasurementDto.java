package com.powerco.telemetry.controller.dto;

import java.util.Date;

import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Relation(collectionRelation = "measurements", itemRelation = "measurement")
public class MeasurementDto {
	private Long id;
	private Date timestamp;	
	private Double value;
	private Long sensorId;
	private Long locationId;
}
