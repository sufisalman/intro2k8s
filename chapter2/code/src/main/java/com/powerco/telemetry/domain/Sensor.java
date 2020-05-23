package com.powerco.telemetry.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Sensor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String secret;
	private Long created;
	private Long updated;
	
	//Autogen FK for Location entity (LOCATION_ID)
	
	@ManyToOne(optional=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Location location;
	
	//Helper constructors - Id will be auto generated.
	public Sensor() {}
	
	public Sensor(String name, String secret, Long created, Long updated, Location location) {
		this.name = name;
		this.secret = secret;
		this.created = created;
		this.updated = updated;
		this.location = location;
	}
	
}
