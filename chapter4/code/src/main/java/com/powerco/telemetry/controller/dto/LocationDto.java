package com.powerco.telemetry.controller.dto;

import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Relation(collectionRelation = "locations", itemRelation = "location")
public class LocationDto {
	private Long id;
	private String zipcode;
	private String name;
}
