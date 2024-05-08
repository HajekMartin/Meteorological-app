package com.example.projekt.Service;

import com.example.projekt.Entity.City;
import com.example.projekt.Entity.State;
import com.example.projekt.Repository.CityRepository;
import com.example.projekt.Repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CityServiceTest {

    @InjectMocks
    private CityService cityService;
    @Mock
    private CityRepository cityRepository;
    private City citySuccessful;
    private City cityUnsuccessful;
    @Mock
    private StateRepository stateRepository;
    private State state;
    @Mock
    private OpenWeatherService openWeatherService;

    @BeforeEach
    void setup() {
        stateRepository = Mockito.mock(StateRepository.class);
        State state = new State();
        state.setId("TEST");
        state.setName("TEST");
        when(stateRepository.findStateById(state.getId())).thenReturn(state);
        cityRepository = Mockito.mock(CityRepository.class);
        citySuccessful = new City();
        citySuccessful.setName("TEST");
        citySuccessful.setState(state);
        citySuccessful.setId(1l);
        cityUnsuccessful = new City();
        cityUnsuccessful.setName("TEST2");
        cityUnsuccessful.setState(state);
        cityUnsuccessful.setId(2l);
        when(cityRepository.findCityById(citySuccessful.getId())).thenReturn(citySuccessful);
        when(cityRepository.findCityIdWithOldestData()).thenReturn(citySuccessful.getId());
        openWeatherService = Mockito.mock(OpenWeatherService.class);
        when(openWeatherService.TestCityId(3l)).thenReturn(true);
        cityService = new CityService(cityRepository, stateRepository, openWeatherService);
    }

    @Test
    void createSuccessful() {
        citySuccessful.setId(3l);
        boolean res = cityService.Create(citySuccessful);
        assertEquals(true, res);
    }

    @Test
    void createUnsuccessful() {
        boolean res = cityService.Create(cityUnsuccessful);
        assertEquals(false, res);
    }

    @Test
    void readById() {
        City res = cityService.ReadById(citySuccessful.getId());
        assertEquals(citySuccessful, res);
    }

    @Test
    void updateSuccessful() {
        boolean res = cityService.Update(citySuccessful);
        assertEquals(true, res);
    }

    @Test
    void updateUnsuccessful() {
        boolean res = cityService.Update(cityUnsuccessful);
        assertEquals(false, res);
    }

    @Test
    void deleteSuccessful() {
        boolean res = cityService.Delete(citySuccessful.getId());
        assertEquals(true, res);
    }

    @Test
    void deleteUnsuccessful() {
        boolean res = cityService.Delete(cityUnsuccessful.getId());
        assertEquals(false, res);
    }

    @Test
    void cityIdWithOldestData() {
        Long res = cityService.CityIdWithOldestData();
        assertEquals(citySuccessful.getId(), res);
    }
}
