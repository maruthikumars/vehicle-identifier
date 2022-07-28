package com.adp.vehicleidentifier.service;

import com.adp.vehicleidentifier.model.VehicleRequest;
import com.adp.vehicleidentifier.model.VehicleResponse;
import com.adp.vehicleidentifier.model.VehicleType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface VehicleService {

    String createVehicles(ArrayList<VehicleType> vehicles);
    List<VehicleResponse> getVehicleDetails(ArrayList<VehicleRequest> vehicles);

    String  updateVehicleDetails(VehicleType vehicle);

    String  deleteVehicle(VehicleType vehicle);
}
