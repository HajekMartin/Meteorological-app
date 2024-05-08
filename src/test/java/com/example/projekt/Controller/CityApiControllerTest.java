package com.example.projekt.Controller;

import com.example.projekt.DTO.CityDto;
import com.example.projekt.DTO.StateDto;
import com.example.projekt.Service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CityApiControllerTest {

    @InjectMocks
    private CityApiController cityApiController;
    @Mock
    private CityService cityService;
    private CityDto cityDtoSuccessful;
    private CityDto cityDtoUnsuccessful;

    @BeforeEach
    void setup() {
        cityService = Mockito.mock(CityService.class);
        // Sucesfull
        cityDtoSuccessful = new CityDto();
        cityDtoSuccessful.setName("TEST");
        cityDtoSuccessful.setId(1l);
        cityDtoSuccessful.setStateId("TEST");
        when(cityService.Create(cityDtoSuccessful)).thenReturn(true);
        when(cityService.Delete(cityDtoSuccessful.getId())).thenReturn(true);
        when(cityService.Update(cityDtoSuccessful)).thenReturn(true);
        // Unsuccessful
        cityDtoUnsuccessful = new CityDto();
        cityDtoUnsuccessful.setName("TEST2");
        cityDtoUnsuccessful.setId(2l);
        cityDtoSuccessful.setStateId("TEST2");
        when(cityService.Create(cityDtoUnsuccessful)).thenReturn(false);
        when(cityService.Delete(cityDtoUnsuccessful.getId())).thenReturn(false);
        when(cityService.Update(cityDtoUnsuccessful)).thenReturn(false);

        cityApiController = new CityApiController(cityService);
    }


    @Test
    void insertCitySuccessful() {
        ResponseEntity<String> res = this.cityApiController.insertCity(cityDtoSuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.OK), res);
    }

    @Test
    void insertCityUnsuccessful() {
        ResponseEntity<String> res = this.cityApiController.insertCity(cityDtoUnsuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), res);
    }

    @Test
    void deleteCitySuccessful() {
        ResponseEntity<String> res = this.cityApiController.deleteCity(cityDtoSuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.OK), res);
    }

    @Test
    void deleteCityUnsuccessful() {
        ResponseEntity<String> res = this.cityApiController.deleteCity(cityDtoUnsuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), res);
    }


    @Test
    void updateCitySuccessful() {
        ResponseEntity<String> res = this.cityApiController.updateCity(cityDtoSuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.OK), res);
    }

    @Test
    void updateCityUnsuccessful() {
        ResponseEntity<String> res = this.cityApiController.updateCity(cityDtoUnsuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), res);
    }
}