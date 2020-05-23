package com.powerco.telemetry.domain;

import lombok.Data;

import java.util.Date;

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
public class Measurement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	private Date timestamp;	
	private Double value;
	
	//Autogen FK for sensor (SENSOR_ID)
	
	@ManyToOne(optional=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Sensor sensor;
	
	//Helper constructors - Id will be auto generated.
	public Measurement(){}
	
	public Measurement(Date timestamp, Double value, Sensor sensor) {
		this.timestamp = timestamp;
		this.value = value;
		this.sensor = sensor;
	}

}
