package com.adp.vehicleidentifier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleFindRequest {
    @NonNull
    private ArrayList<VehicleRequest> vehicles;
}
