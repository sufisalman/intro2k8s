package com.powerco.telemetry.controller.dto;

import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Relation(collectionRelation = "sensors", itemRelation = "sensor")
public class SensorDto {
	private Long id;
	private String name;
	private Long locationId;
}
