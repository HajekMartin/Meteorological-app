package com.example.projekt.Service;

import com.example.projekt.Entity.City;
import com.example.projekt.Entity.Measurement;
import com.example.projekt.Entity.State;
import com.example.projekt.Repository.CityRepository;
import com.example.projekt.Repository.MeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MeasurementServiceTest {

    @InjectMocks
    private MeasurementService measurementService;
    @Mock
    private MeasurementRepository measurementRepository;
    private Measurement measurementSuccessful;
    private Measurement measurementUnsuccessful;
    @Mock
    private CityRepository cityRepository;
    private City city;
    private State state;
    private Long duration;
    @BeforeEach
    void setup() {
        state = new State();
        state.setName("TEST");
        state.setId("TEST");
        city = new City();
        city.setId(1l);
        city.setState(state);
        city.setName("TEST");
        duration = 1l;

        measurementRepository = Mockito.mock(MeasurementRepository.class);
        measurementSuccessful = new Measurement();
        measurementSuccessful.setCity(city);
        measurementSuccessful.setId(1l);
        measurementSuccessful.setTemperature(1d);
        measurementSuccessful.setRain(1d);
        measurementSuccessful.setWindSpeed(1d);
        measurementSuccessful.setDate(1l);

        measurementUnsuccessful = new Measurement();
        measurementUnsuccessful.setCity(city);
        measurementUnsuccessful.setId(2l);
        measurementUnsuccessful.setRain(2d);
        measurementUnsuccessful.setWindSpeed(2d);
        measurementUnsuccessful.setDate(2l);

        when(measurementRepository.exitsMeasurementForCityByDate(measurementSuccessful.getDate(), measurementSuccessful.getCity().getId())).thenReturn(false);
        when(measurementRepository.findMeasurementById(measurementSuccessful.getId())).thenReturn(measurementSuccessful);
        when(measurementRepository.findMeasurementById(measurementUnsuccessful.getId())).thenReturn(null);
        when(measurementRepository.actualMeasurement(measurementSuccessful.getId())).thenReturn(measurementSuccessful);
        cityRepository = Mockito.mock(CityRepository.class);

        this.measurementService = new MeasurementService(measurementRepository, cityRepository);
    }

    @Test
    void createSuccessful() {
        boolean res = measurementService.Create(measurementSuccessful);
        assertEquals(true, res);
    }

    @Test
    void createUnsuccessful() {
        boolean res = measurementService.Create(measurementUnsuccessful);
        assertEquals(false, res);
    }

    @Test
    void readById() {
        Measurement res = measurementService.ReadById(measurementSuccessful.getId());
        assertEquals(measurementSuccessful, res);
    }

    @Test
    void updateSuccessful() {
        boolean res = measurementService.Update(measurementSuccessful);
        assertEquals(true, res);
    }

    @Test
    void updateUnsuccessful() {
        boolean res = measurementService.Update(measurementUnsuccessful);
        assertEquals(false, res);
    }

    @Test
    void deleteSuccessful() {
        boolean res = measurementService.Delete(measurementSuccessful.getId());
        assertEquals(true, res);
    }

    @Test
    void deleteUnsuccessful() {
        boolean res = measurementService.Delete(measurementUnsuccessful.getId());
        assertEquals(false, res);
    }

    @Test
    void actualMeasurement() {
        Measurement measurement = measurementService.ActualMeasurement(measurementSuccessful.getId());
        assertEquals(measurementSuccessful, measurement);
    }
}