package com.example.projekt.Controller;

import com.example.projekt.DTO.CityDto;
import com.example.projekt.DTO.StateDto;
import com.example.projekt.Entity.City;
import com.example.projekt.Entity.State;
import com.example.projekt.Service.CityService;
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

class CityApiReadControllerTest {

    @InjectMocks
    private CityApiReadController cityApiReadController;
    @Mock
    private CityService cityService;
    private CityDto cityDtoSuccessful;
    private CityDto cityDtoUnsuccessful;

    @BeforeEach
    void setup() {
        cityService = Mockito.mock(CityService.class);
        // Successful
        cityDtoSuccessful = new CityDto();
        cityDtoSuccessful.setStateId("TEST");
        cityDtoSuccessful.setId(1l);
        cityDtoSuccessful.setName("TEST");
        State state = new State();
        state.setId("TEST");
        state.setName("TEST");
        City city = new City();
        city.setId(cityDtoSuccessful.getId());
        city.setState(state);
        city.setName(cityDtoSuccessful.getName());
        when(cityService.ReadById(cityDtoSuccessful.getId())).thenReturn(city);
        // Unsuccessful
        cityDtoUnsuccessful = new CityDto();
        cityDtoUnsuccessful.setId(2l);
        cityDtoUnsuccessful.setName("TEST2");
        cityDtoUnsuccessful.setStateId("TEST2");
        when(cityService.ReadById(cityDtoUnsuccessful.getId())).thenReturn(null);

        this.cityApiReadController = new CityApiReadController(cityService);
    }

    @Test
    void getCitiesSuccessful() {
        ResponseEntity<Iterable<CityDto>> res = this.cityApiReadController.getCities();
        ArrayList<CityDto> emptyList = new ArrayList<>();
        assertEquals(ResponseEntity.ok(emptyList), res);
    }

    @Test
    void getCitySuccessful() {
        ResponseEntity<CityDto> res = this.cityApiReadController.getCity(this.cityDtoSuccessful.getId());
        assertEquals(ResponseEntity.ok(cityDtoSuccessful), res);
    }

    @Test
    void getCityUnsuccessful() {
        ResponseEntity<CityDto> res = this.cityApiReadController.getCity(this.cityDtoUnsuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), res);
    }
}