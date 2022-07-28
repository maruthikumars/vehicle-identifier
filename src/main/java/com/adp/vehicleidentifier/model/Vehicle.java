package com.adp.vehicleidentifier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(VehicleId.class)
public class Vehicle {
    @Id
    private String vehicleType;
    @Id
    private String position;
    private String material;
    private String powertrain;
}
