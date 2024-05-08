package com.example.projekt.Controller;

import com.example.projekt.DTO.MeasurementDto;
import com.example.projekt.Service.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MeasurementApiControllerTest {

    @InjectMocks
    private MeasurementApiController measurementApiController;
    @Mock
    private MeasurementService measurementService;
    private MeasurementDto measurementDtoSuccessful;
    private MeasurementDto measurementDtoUnsuccessful;

    @BeforeEach
    void setup() {
        measurementService = Mockito.mock(MeasurementService.class);
        // Sucesfull
        measurementDtoSuccessful = new MeasurementDto();
        measurementDtoSuccessful.setDate(1l);
        measurementDtoSuccessful.setId(1l);
        measurementDtoSuccessful.setRain(1d);
        measurementDtoSuccessful.setTemperature(1d);
        measurementDtoSuccessful.setWindSpeed(1d);
        measurementDtoSuccessful.setCityId(1l);
        when(measurementService.Create(measurementDtoSuccessful)).thenReturn(true);
        when(measurementService.Delete(measurementDtoSuccessful.getId())).thenReturn(true);
        when(measurementService.Update(measurementDtoSuccessful)).thenReturn(true);
        // Unsuccessful
        measurementDtoUnsuccessful = new MeasurementDto();
        measurementDtoUnsuccessful.setDate(2l);
        measurementDtoUnsuccessful.setId(2l);
        measurementDtoUnsuccessful.setRain(2d);
        measurementDtoUnsuccessful.setTemperature(2d);
        measurementDtoUnsuccessful.setWindSpeed(2d);
        measurementDtoUnsuccessful.setCityId(2l);
        when(measurementService.Create(measurementDtoUnsuccessful)).thenReturn(false);
        when(measurementService.Delete(measurementDtoUnsuccessful.getId())).thenReturn(false);
        when(measurementService.Update(measurementDtoUnsuccessful)).thenReturn(false);

        measurementApiController = new MeasurementApiController(measurementService);
    }

    @Test
    void insertMeasurementSuccessful() {
        ResponseEntity<String> res = this.measurementApiController.insertMeasurement(measurementDtoSuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.OK), res);
    }

    @Test
    void insertMeasurementUnsuccessful() {
        ResponseEntity<String> res = this.measurementApiController.insertMeasurement(measurementDtoUnsuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), res);
    }

    @Test
    void deleteMeasurementSuccessful() {
        ResponseEntity<String> res = this.measurementApiController.deleteMeasurement(measurementDtoSuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.OK), res);
    }

    @Test
    void deleteMeasurementUnsuccessful() {
        ResponseEntity<String> res = this.measurementApiController.deleteMeasurement(measurementDtoUnsuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), res);
    }

    @Test
    void updateMeasurementSuccessful() {
        ResponseEntity<String> res = this.measurementApiController.updateMeasurement(measurementDtoSuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.OK), res);
    }

    @Test
    void updateMeasurementUnsuccessful() {
        ResponseEntity<String> res = this.measurementApiController.updateMeasurement(measurementDtoUnsuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), res);
    }
}