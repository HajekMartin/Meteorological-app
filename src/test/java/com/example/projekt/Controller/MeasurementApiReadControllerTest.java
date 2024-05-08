package com.example.projekt.Controller;

import com.example.projekt.DTO.AverageValuesDto;
import com.example.projekt.DTO.CityDto;
import com.example.projekt.DTO.MeasurementDto;
import com.example.projekt.Entity.City;
import com.example.projekt.Entity.Measurement;
import com.example.projekt.Entity.State;
import com.example.projekt.Service.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MeasurementApiReadControllerTest {

    @InjectMocks
    private MeasurementApiReadController measurementApiReadController;
    @Mock
    private MeasurementService measurementService;
    private MeasurementDto measurementDtoSuccessful;
    private MeasurementDto measurementDtoUnsuccessful;
    private AverageValuesDto averageValuesDto;
    private Long duration;

    @BeforeEach
    void setup() {
        measurementService = Mockito.mock(MeasurementService.class);
        // Successful
        duration = 1l;
        measurementDtoSuccessful = new MeasurementDto();
        measurementDtoSuccessful.setDate(1l);
        measurementDtoSuccessful.setId(1l);
        measurementDtoSuccessful.setRain(1d);
        measurementDtoSuccessful.setTemperature(1d);
        measurementDtoSuccessful.setWindSpeed(1d);
        measurementDtoSuccessful.setCityId(1l);
        State state = new State();
        state.setId("TEST");
        state.setName("TEST");
        City city = new City();
        city.setId(1l);
        city.setState(state);
        city.setName("TEST");
        Measurement measurement = new Measurement();
        measurement.setDate(measurementDtoSuccessful.getDate());
        measurement.setId(measurementDtoSuccessful.getId());
        measurement.setRain(measurementDtoSuccessful.getRain());
        measurement.setTemperature(measurementDtoSuccessful.getTemperature());
        measurement.setWindSpeed(measurementDtoSuccessful.getTemperature());
        measurement.setCity(city);
        averageValuesDto = new AverageValuesDto();
        averageValuesDto.setRain(1d);
        averageValuesDto.setWindSpeed(1d);
        averageValuesDto.setWindSpeed(1d);
        when(measurementService.ReadById(measurementDtoSuccessful.getId())).thenReturn(measurement);
        when(measurementService.ActualMeasurement(measurementDtoSuccessful.getCityId())).thenReturn(measurement);
        when(measurementService.AverageValues(measurementDtoSuccessful.getCityId(), duration)).thenReturn(averageValuesDto);
        // Unsuccessful
        measurementDtoUnsuccessful = new MeasurementDto();
        measurementDtoUnsuccessful.setDate(2l);
        measurementDtoUnsuccessful.setId(2l);
        measurementDtoUnsuccessful.setRain(2d);
        measurementDtoUnsuccessful.setTemperature(2d);
        measurementDtoUnsuccessful.setWindSpeed(2d);
        measurementDtoUnsuccessful.setCityId(2l);
        when(measurementService.ReadById(measurementDtoUnsuccessful.getId())).thenReturn(null);
        when(measurementService.ActualMeasurement(measurementDtoUnsuccessful.getCityId())).thenReturn(null);
        this.measurementApiReadController = new MeasurementApiReadController(measurementService);
    }

    @Test
    void getMeasurementsSuccessful() {
        ResponseEntity<Iterable<MeasurementDto>> res = this.measurementApiReadController.getMeasurements();
        ArrayList<CityDto> emptyList = new ArrayList<>();
        assertEquals(ResponseEntity.ok(emptyList), res);
    }

    @Test
    void getMeasurementSuccessful() {
        ResponseEntity<MeasurementDto> res = this.measurementApiReadController.getMeasurement(this.measurementDtoSuccessful.getId());
        assertEquals(ResponseEntity.ok(measurementDtoSuccessful), res);
    }

    @Test
    void getMeasurementUnsuccessful() {
        ResponseEntity<MeasurementDto> res = this.measurementApiReadController.getMeasurement(this.measurementDtoUnsuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), res);
    }

    @Test
    void actualSuccessful() {
        ResponseEntity<MeasurementDto> res = this.measurementApiReadController.actual(this.measurementDtoSuccessful.getId());
        assertEquals(ResponseEntity.ok(measurementDtoSuccessful), res);
    }

    @Test
    void actualUnsuccessful() {
        ResponseEntity<MeasurementDto> res = this.measurementApiReadController.actual(this.measurementDtoUnsuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), res);
    }

    @Test
    void averageSuccessful() {
        ResponseEntity<AverageValuesDto> res = this.measurementApiReadController.average(this.measurementDtoSuccessful.getCityId(), duration);
        assertEquals(ResponseEntity.ok(averageValuesDto), res);
    }
}