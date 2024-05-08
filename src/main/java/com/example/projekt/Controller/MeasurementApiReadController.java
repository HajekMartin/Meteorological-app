package com.example.projekt.Controller;

import com.example.projekt.DTO.AverageValuesDto;
import com.example.projekt.DTO.MeasurementDto;
import com.example.projekt.Entity.Measurement;
import com.example.projekt.Service.LoggerService;
import com.example.projekt.Service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class MeasurementApiReadController {

    @Autowired
    private MeasurementService measurementService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "MeasurementApiReadController";

    public MeasurementApiReadController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/api/measurement/findAll")
    public ResponseEntity<Iterable<MeasurementDto>> getMeasurements() {
        loggerService.LogControlller(controllerName, "/api/measurement/findAll", new ArrayList<String>(Arrays.asList()));
        Iterable<Measurement> measurements = measurementService.ReadAll();
        List<MeasurementDto> result = new ArrayList<>();
        for (Measurement measurement: measurements) {
            result.add(new MeasurementDto(measurement));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/measurement/find")
    public ResponseEntity<MeasurementDto> getMeasurement(@RequestParam Long id) {
        loggerService.LogControlller(controllerName, "/api/measurement/find", new ArrayList<String>(Arrays.asList(id.toString())));
        Measurement measurement = measurementService.ReadById(id);
        if (measurement == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new MeasurementDto(measurement));
    }

    @GetMapping("/api/measurement/actual")
    public ResponseEntity<MeasurementDto> actual(@RequestParam Long cityId) {
        loggerService.LogControlller(controllerName, "/api/measurement/actual", new ArrayList<String>(Arrays.asList(cityId.toString())));
        Measurement measurement = measurementService.ActualMeasurement(cityId);
        if (measurement == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MeasurementDto measurementDto = new MeasurementDto(measurement);
        return ResponseEntity.ok(measurementDto);
    }

    @GetMapping("/api/measurement/average")
    public ResponseEntity<AverageValuesDto> average(@RequestParam Long cityId, @RequestParam Long duration) {
        loggerService.LogControlller(controllerName, "/api/measurement/average", new ArrayList<String>(Arrays.asList(cityId.toString(), duration.toString())));
        return ResponseEntity.ok(measurementService.AverageValues(cityId, duration));
    }
}
