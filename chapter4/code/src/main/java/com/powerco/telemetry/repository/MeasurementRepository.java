package com.powerco.telemetry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.powerco.telemetry.domain.Measurement;
import com.powerco.telemetry.domain.Sensor;

//provides full set of CRUD operations
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
	
	
	//Note the use of  "m.sensor.location.id" deep graph!
	
	//Unrestricted columns are returned. Simulation of "SELECT *"
	
	@Query("SELECT m FROM Measurement m WHERE m.sensor.id = :sensorId AND m.sensor.location.id = :locationId")
	List<Measurement> findAllMeasurementsBySensor(@Param("locationId") Long locationId, @Param("sensorId") Long sensorId);
	
	
	
	/*   Note the change in return type, if specific columns are not used. Columns can also be filtered out in a REST friendly presentation POJO.
	
	@Query("SELECT m.id, m.value, m.timestamp FROM Measurement m WHERE m.sensor.id = :sensorId AND m.sensor.location.id = :locationId")
	List<Object[]> findAllMeasurementsBySensor(@Param("locationId") Long locationId, @Param("sensorId") Long sensorId);
	
	*/
	
	
	@Query("SELECT m FROM Measurement m WHERE m.id= :id AND m.sensor.id=:sensorId AND m.sensor.location.id=:locationId")
    Measurement findMeasurementBySensorId(@Param("locationId") Long locationId, 
    								 @Param("sensorId") Long sensorId, 
    								 @Param("id") Long id);
}
