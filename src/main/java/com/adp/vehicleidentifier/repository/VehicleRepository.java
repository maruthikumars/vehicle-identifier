package com.adp.vehicleidentifier.repository;

import com.adp.vehicleidentifier.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    @Query(nativeQuery =true, value = "select distinct(vehicle_type) from vehicle where material = :material and position IN (:position) and powertrain = :powertrain")
    String findVehicleType(@Param("material") String material, @Param("position") List<String> position, @Param("powertrain") String powertrain);

    Vehicle findByVehicleTypeAndPosition(String vehicleType, String position);
}
