package com.example.projekt.Controller;

import com.example.projekt.DTO.MeasurementDto;
import com.example.projekt.Service.LoggerService;
import com.example.projekt.Service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@ConditionalOnProperty(name = "readmode", havingValue = "false")
public class MeasurementApiController {

    @Autowired
    private MeasurementService measurementService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "MeasurementApiController";

    public MeasurementApiController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping("/api/measurement/add")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public ResponseEntity<String> insertMeasurement(@RequestBody MeasurementDto measurementDto) {
        loggerService.LogControlller(controllerName, "/api/measurement/add", new ArrayList<String>(Arrays.asList(measurementDto.toString())));
        if (measurementService.Create(measurementDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/api/measurement/delete")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public ResponseEntity<String> deleteMeasurement(@RequestParam Long id) {
        loggerService.LogControlller(controllerName, "/api/measurement/delete", new ArrayList<String>(Arrays.asList(id.toString())));
        if (measurementService.Delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/measurement/edit")
    @ConditionalOnProperty(name = "readmode", havingValue = "0")
    public ResponseEntity<String> updateMeasurement(@RequestBody MeasurementDto measurementDto) {
        loggerService.LogControlller(controllerName, "/api/measurement/edit", new ArrayList<String>(Arrays.asList(measurementDto.toString())));
        if (measurementService.Update(measurementDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
