package com.adp.vehicleidentifier.controller;

import com.adp.vehicleidentifier.exception.VehicleNotFound;
import com.adp.vehicleidentifier.model.VehicleAddRequest;
import com.adp.vehicleidentifier.model.VehicleFindRequest;
import com.adp.vehicleidentifier.model.VehicleResponse;
import com.adp.vehicleidentifier.model.VehicleType;
import com.adp.vehicleidentifier.service.VehicleService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/vehicles")
@Api(value = "VehicleIdentifierController", description = "REST APIs add and identifying Vehicles")
public class VehicleIdentifierController {
    private static final Logger log = LoggerFactory.getLogger(VehicleIdentifierController.class);

    private static final String RESILIENCE4J_INSTANCE_NAME = "vehicleindentifier";
    private static final String FALLBACK_METHOD = "fallback";

    @Autowired
    VehicleService service;

    @ApiOperation(value = "Add vehicles ", response = Iterable.class, tags = "addVehicles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")})
    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<String> addVehicles(@RequestBody @Valid VehicleAddRequest vehicleAddRequest) {
        log.info("started processing addVehicles() with= {}", vehicleAddRequest);
        return ResponseEntity.ok(service.createVehicles(vehicleAddRequest.getVehicles()));
    }

    @PostMapping(value = "/identify", produces = "application/json")
    @ApiOperation(value = "Identify vehicles ", response = Iterable.class, tags = "identifyVehicleTypes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")})
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    @Cacheable
    public ResponseEntity<List<VehicleResponse>> identifyVehicleTypes(@RequestBody VehicleFindRequest vehicleFindRequest) {
        log.info("Starting identifying the Vehicle");

        List<VehicleResponse> responseList = service.getVehicleDetails(vehicleFindRequest.getVehicles());

        if (responseList.isEmpty()) {
            throw new VehicleNotFound("No Vehicle found");
        }

        log.info("Completed identifying the Vehicle  {}", responseList);
        return ResponseEntity.ok(responseList);
    }

    @PutMapping(value = "/modify")
    @ApiOperation(value = "Modify vehicle details ", response = Iterable.class, tags = "updateVehicleTypes")
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    @Cacheable
    public ResponseEntity<String> updateVehicle(@RequestBody VehicleType vehicleType) {
        log.info("Starting updating the Vehicle");

        String response = service.updateVehicleDetails(vehicleType);

        log.info("Updated Vehicles  {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/remove")
    @ApiOperation(value = "Delete vehicle ", response = Iterable.class, tags = "deleteVehicle")
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    @Cacheable
    public ResponseEntity<String> deleteVehicle(@RequestBody VehicleType vehicleType) {
        log.info("Starting deleting the Vehicle");

        String response = service.deleteVehicle(vehicleType);

        log.info("deleted Vehicle  {}", response);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<List<VehicleResponse>> fallback(Exception ex) {
        log.warn("fallback executed");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}