package com.example.projekt.Controller;

import com.example.projekt.DTO.StateDto;
import com.example.projekt.Entity.State;
import com.example.projekt.Service.StateService;
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

class StateApiReadControllerTest {

    @InjectMocks
    private StateApiReadController stateApiReadController;
    @Mock
    private StateService stateService;
    private StateDto stateDtoSuccessful;
    private StateDto stateDtoUnsuccessful;

    @BeforeEach
    void setup() {
        stateService = Mockito.mock(StateService.class);
        // Successful
        stateDtoSuccessful = new StateDto();
        stateDtoSuccessful.setName("TEST");
        stateDtoSuccessful.setId("TEST");
        State state = new State();
        state.setId(stateDtoSuccessful.getId());
        state.setName(stateDtoSuccessful.getName());
        when(stateService.ReadById(stateDtoSuccessful.getId())).thenReturn(state);
        // Unsuccessful
        stateDtoUnsuccessful = new StateDto();
        stateDtoUnsuccessful.setName("TEST2");
        stateDtoUnsuccessful.setId("TEST2");
        when(stateService.ReadById(stateDtoUnsuccessful.getId())).thenReturn(null);

        this.stateApiReadController = new StateApiReadController(stateService);
    }

    @Test
    public void getStatesSuccessful() {
        ResponseEntity<Iterable<StateDto>> res = this.stateApiReadController.getStates();
        ArrayList<StateDto> emptyList = new ArrayList<>();
        assertEquals(ResponseEntity.ok(emptyList), res);
    }

    @Test
    public void getStateSuccessful() {
        ResponseEntity<StateDto> res = this.stateApiReadController.getState(this.stateDtoSuccessful.getId());
        assertEquals(ResponseEntity.ok(stateDtoSuccessful), res);
    }

    @Test
    public void getStateUnsuccessful() {
        ResponseEntity<StateDto> res = this.stateApiReadController.getState(this.stateDtoUnsuccessful.getId());
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), res);
    }

}