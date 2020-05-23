package com.powerco.telemetry.repository;

import com.powerco.telemetry.domain.Sensor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

///provides full set of CRUD operations
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
	
	@Query("SELECT s FROM Sensor s WHERE s.location.id = :locationId")
    List<Sensor> findAllSensorsByLocationId(@Param("locationId") Long locationId);
	
	@Query("SELECT s FROM Sensor s WHERE s.id = :sensorId AND s.location.id = :locationId")
    Sensor findSensorByLocationId(@Param("locationId") Long locationId, @Param("sensorId") Long sensorId);


}
