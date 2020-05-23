package com.powerco.telemetry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.powerco.telemetry.domain.Location;
import com.powerco.telemetry.domain.Measurement;
import com.powerco.telemetry.domain.Sensor;
import com.powerco.telemetry.repository.LocationRepository;
import com.powerco.telemetry.repository.MeasurementRepository;
import com.powerco.telemetry.repository.SensorRepository;

@Service
@Transactional
public class TelemetryService {
	
	//tmp
	private static final String SENSOR_SECRET = "-secret";  
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Autowired
	private SensorRepository sensorRepo;
	
	@Autowired
	private MeasurementRepository measurementRepo;
	
	/********** Location services ************/
	public List<Location> getAllLocations(){
		return locationRepo.findAll();
	}
	
	public Location getOneLocation(Long id) {
    	return locationRepo.getOne(id);
	}
	
	public Location createLocation(Location newLocation) {
		return locationRepo.save(newLocation);
	}
	
	public void deleteLocation(Long id) {
		locationRepo.deleteById(id);
	}
	
	public Location updateLocation(Location newLocation, Long id) {
		return locationRepo.findById(id)
    		.map(location -> {
    			location.setName(newLocation.getName());
    			location.setZipcode(newLocation.getZipcode());
    			return locationRepo.save(location);
    		})
    		.orElseGet(() -> {
    			newLocation.setId(id);
    			return locationRepo.save(newLocation);
    		});
	}
	
	/********** Sensor Services ************/	
	public List<Sensor> findAllSensors(Long locationId) {
		return sensorRepo.findAllSensorsByLocationId(locationRepo.getOne(locationId).getId());
	}	

	public Sensor getOneSensor(Long locationId, Long sensorId) {
		return sensorRepo.findSensorByLocationId(locationId, sensorId);
	}

	public Sensor createSensor(Sensor newSensor) {
		Sensor sensor = new Sensor();
		sensor.setName(newSensor.getName());
		sensor.setSecret(newSensor.getName() + SENSOR_SECRET);
		sensor.setLocation(newSensor.getLocation());
		//sensor created and updated
		sensor.setCreated(System.currentTimeMillis());
		sensor.setUpdated(System.currentTimeMillis());
		return sensorRepo.save(sensor);
	}
	
	public void deleteSensor(Long locationId, Long sensorId) {
		sensorRepo.deleteById(sensorId);
	}

	public Sensor updateSensor(Sensor newSensor) {
		return sensorRepo.findById(newSensor.getId())
    		.map(sensor -> {
    			sensor.setName(newSensor.getName());
    			sensor.setSecret(newSensor.getName()+ SENSOR_SECRET);
    			sensor.setLocation(newSensor.getLocation());
    			//sensor only updated
    			sensor.setUpdated(System.currentTimeMillis());
    			return sensorRepo.save(sensor);
    		})
    		.orElseGet(() -> {
    			//newSensor.setId(newSensor.getId());
    			return sensorRepo.save(newSensor);
    		});
	}
	
	/********** Measurement Services ************/
	public List<Measurement> getAllMeasurements(Long locationId, Long sensorId) {
		return measurementRepo.findAllMeasurementsBySensor(locationId, sensorId);
	}
	
	
	public Measurement getOneMeasurement(Long locationId, Long sensorId, Long id) {
		return measurementRepo.findMeasurementBySensorId(locationId, sensorId, id);
			
	}
	
	public Measurement createNewMeasurement(Measurement newMeasurement) {
		Measurement measurement = new Measurement();
		measurement.setValue(newMeasurement.getValue());
		measurement.setTimestamp(newMeasurement.getTimestamp());		
		measurement.setSensor(newMeasurement.getSensor());		
		return measurementRepo.save(measurement);
	}
	
	
	public void deleteMeasurement(Long id) {
		measurementRepo.deleteById(id);
	}
	
	
	public Measurement updateMeasurement(Measurement newMeasurement) {
		return measurementRepo.findById(newMeasurement.getId())
    		.map(measurement -> {
    			measurement.setTimestamp(newMeasurement.getTimestamp());
    			measurement.setValue(newMeasurement.getValue());
    			return measurementRepo.save(measurement);
    		})
    		.orElseGet(() -> {
    			//newMeasurement.setId(id);
    			return measurementRepo.save(newMeasurement);
    		});
	}	
	

}
