package com.example.projekt.Controller;

import com.example.projekt.DTO.StateDto;
import com.example.projekt.Service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StateApiControllerTest {

    @InjectMocks
    private StateApiController stateApiController;
    @Mock
    private StateService stateService;
    private StateDto stateDtoSuccessful;
    private StateDto stateDtoUnsuccessful;

    @BeforeEach
    void setup() {
        stateService = Mockito.mock(StateService.class);
        // Sucesfull
        stateDtoSuccessful = new StateDto();
        stateDtoSuccessful.setName("TEST");
        stateDtoSuccessful.setId("TEST");
        when(stateService.Create(stateDtoSuccessful)).thenReturn(true);
        when(stateService.Delete(stateDtoSuccessful.getId())).thenReturn(true);
        when(stateService.Update(stateDtoSuccessful)).thenReturn(true);
        // Unsuccessful
        stateDtoUnsuccessful = new StateDto();
        stateDtoUnsuccessful.setName("TEST2");
        stateDtoUnsuccessful.setId("TEST2");
        when(stateService.Create(stateDtoUnsuccessful)).thenReturn(false);
        when(stateService.Delete(stateDtoUnsuccessful.getId())).thenReturn(false);
        when(stateService.Update(stateDtoUnsuccessful)).thenReturn(false);

        stateApiController = new StateApiController(stateService);
    }

    @Test
    void insertStateSuccessful() {
        ResponseEntity<String> res = this.stateApiController.insertState(stateDtoSuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.OK), res);
    }

    @Test
    void insertStateUnsuccessful() {
        ResponseEntity<String> res = this.stateApiController.insertState(stateDtoUnsuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), res);
    }

    @Test
    void deleteStateSuccessful() {
        ResponseEntity<String> res = this.stateApiController.deleteState(stateDtoSuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.OK), res);
    }

    @Test
    void deleteStateUnsuccessful() {
        ResponseEntity<String> res = this.stateApiController.deleteState(stateDtoUnsuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), res);
    }

    @Test
    void updateStateSuccessful() {
        ResponseEntity<String> res = this.stateApiController.updateState(stateDtoSuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.OK), res);
    }

    @Test
    void updateStateUnsxuccessful() {
        ResponseEntity<String> res = this.stateApiController.updateState(stateDtoUnsuccessful);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), res);
    }
}