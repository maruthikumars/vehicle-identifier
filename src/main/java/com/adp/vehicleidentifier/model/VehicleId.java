package com.adp.vehicleidentifier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleId implements Serializable {
    private String vehicleType;
    private String position;
}
