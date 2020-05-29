package com.powerco.telemetry.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Location {

	// GenerationType.IDENITY is used to keep Id's unique intra-table but not
	// inter-table.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String zipcode;

	private String name;

	public Location() {
	}

	public Location(String zipcode, String name) {
		this.zipcode = zipcode;
		this.name = name;
	}

}