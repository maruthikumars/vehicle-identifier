package com.adp.vehicleidentifier.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleType {

    @ApiModelProperty(notes = "vehicle type", name = "vehicleType", required = true, value = "Big Wheel")
    private String vehicleType;

    @ApiModelProperty(notes = "vehicle frame", name = "frame", required = true, value = "plastic")
    private Frame frame;

    @ApiModelProperty(notes = "wheels", name = "wheels", required = true, value = "{position=left rear, material=plastic}")
    private Set<Wheel> wheels;

    @ApiModelProperty(notes = "powertrain", name = "powertrain", required = true, value = "human")
    private String powertrain;
}
