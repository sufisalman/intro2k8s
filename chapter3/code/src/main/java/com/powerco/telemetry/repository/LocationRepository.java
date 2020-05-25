package com.powerco.telemetry.repository;

import com.powerco.telemetry.domain.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//provides full set of CRUD operations
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{

}