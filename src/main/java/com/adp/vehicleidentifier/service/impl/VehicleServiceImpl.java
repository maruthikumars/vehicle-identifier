package com.adp.vehicleidentifier.service.impl;

import com.adp.vehicleidentifier.model.Vehicle;
import com.adp.vehicleidentifier.model.VehicleRequest;
import com.adp.vehicleidentifier.model.VehicleResponse;
import com.adp.vehicleidentifier.model.VehicleType;
import com.adp.vehicleidentifier.model.Wheel;
import com.adp.vehicleidentifier.repository.VehicleRepository;
import com.adp.vehicleidentifier.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {
    private static final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    VehicleRepository repository;

    @Override
    public String createVehicles(ArrayList<VehicleType> vehicles) {
        List<Vehicle> list = vehicles.stream().flatMap(vehicleType -> vehicleType.getWheels().stream().map(wheel -> {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleType(vehicleType.getVehicleType());
            vehicle.setMaterial(vehicleType.getFrame().getMaterial());
            vehicle.setPowertrain(vehicleType.getPowertrain());
            vehicle.setPosition(wheel.getPosition());
            return vehicle;
        })).collect(Collectors.toList());

        repository.saveAll(list);
        log.info("Completed processing addVehicles()");
        return "Vehicles added";
    }

    @Override
    public List<VehicleResponse> getVehicleDetails(ArrayList<VehicleRequest> vehicles) {
        List<VehicleResponse> responseList = new ArrayList<>();
        for (VehicleRequest vehicle : vehicles) {
            List<String> positions = new ArrayList<>();
            for (Wheel wheel : vehicle.getWheels()) {
                positions.add(wheel.getPosition());
            }


            String vehicleType = repository.findVehicleType(vehicle.getFrame().getMaterial(), positions, vehicle.getPowertrain());
            if (vehicleType != null) {
                responseList.add(new VehicleResponse(vehicle.getId(), vehicleType));
            } else {
                responseList.add(new VehicleResponse(vehicle.getId(), "No Vehicle Found"));
            }
        }

        return responseList;
    }

    @Override
    public String updateVehicleDetails(VehicleType vehicleType) {
        vehicleType.getWheels().forEach(wheel -> {
            Vehicle vehicle = repository.findByVehicleTypeAndPosition(vehicleType.getVehicleType(), wheel.getPosition());
            if (vehicle != null) {
                repository.save(new Vehicle(vehicleType.getVehicleType(), wheel.getPosition(), vehicleType.getFrame().getMaterial(), vehicleType.getPowertrain()));
                log.info("Vehicle details updated");
            } else {
                log.info("Vehicles Not found and not updated");
            }
        });

        log.info("Completed updateVehicleDetails()");
        return "Vehicle details updated";
    }

    @Override
    public String deleteVehicle(VehicleType vehicleType) {
        vehicleType.getWheels().forEach(wheel -> {
            Vehicle vehicle = repository.findByVehicleTypeAndPosition(vehicleType.getVehicleType(), wheel.getPosition());
            if (vehicle != null) {
                repository.delete(new Vehicle(vehicleType.getVehicleType(), wheel.getPosition(), vehicleType.getFrame().getMaterial(), vehicleType.getPowertrain()));
                log.info("Vehicle deleted");
            } else {
                log.info("Vehicles Not found and not deleted");
            }
        });
        return "Vehicle deleted";
    }
}
