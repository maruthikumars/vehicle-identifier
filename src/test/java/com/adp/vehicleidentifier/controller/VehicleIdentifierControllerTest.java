package com.adp.vehicleidentifier.controller;

import com.adp.vehicleidentifier.model.Frame;
import com.adp.vehicleidentifier.model.VehicleAddRequest;
import com.adp.vehicleidentifier.model.VehicleFindRequest;
import com.adp.vehicleidentifier.model.VehicleRequest;
import com.adp.vehicleidentifier.model.VehicleType;
import com.adp.vehicleidentifier.model.Wheel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VehicleIdentifierController.class)
public class VehicleIdentifierControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateVehicle() throws Exception {
        ArrayList<VehicleType> vehicles = new ArrayList<>();
        Set<Wheel> wheels = new HashSet<>();
        Wheel wheel1 = new Wheel("left rear", "plastic");
        Wheel wheel2 = new Wheel("right rear", "plastic");
        wheels.add(wheel1);
        wheels.add(wheel2);
        vehicles.add(new VehicleType("Big Wheel", new Frame("plastic"), wheels, "human"));

        VehicleAddRequest request = new VehicleAddRequest(vehicles);

        mockMvc.perform(post("/v1/vehicles/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @Test
    void shouldReturnVehicle() throws Exception {
        Set<Wheel> wheels = new HashSet<>();
        Wheel wheel1 = new Wheel("left rear", "plastic");
        Wheel wheel2 = new Wheel("right rear", "plastic");
        wheels.add(wheel1);
        wheels.add(wheel2);
        VehicleRequest vehicle = new VehicleRequest("vehicleId1", new Frame("plastic"), wheels, "human");
        ArrayList<VehicleRequest> vehicles = new ArrayList<>();
        vehicles.add(vehicle);
        VehicleFindRequest request = new VehicleFindRequest(vehicles);

        mockMvc.perform(post("/v1/vehicles/identify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @Test
    void shouldModifyVehicle() throws Exception {
        Set<Wheel> wheels = new HashSet<>();
        Wheel wheel1 = new Wheel("left rear", "plastic");
        Wheel wheel2 = new Wheel("right rear", "plastic");
        wheels.add(wheel1);
        wheels.add(wheel2);
        VehicleType vehicleType = new VehicleType("Big Wheel", new Frame("plastic"), wheels, "human");

        mockMvc.perform(put("/v1/vehicles/modify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleType)))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @Test
    void shouldRemoveVehicle() throws Exception {
        Set<Wheel> wheels = new HashSet<>();
        Wheel wheel1 = new Wheel("left rear", "plastic");
        Wheel wheel2 = new Wheel("right rear", "plastic");
        wheels.add(wheel1);
        wheels.add(wheel2);
        VehicleType vehicleType = new VehicleType("Big Wheel", new Frame("plastic"), wheels, "human");

        mockMvc.perform(put("/v1/vehicles/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleType)))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }
}
