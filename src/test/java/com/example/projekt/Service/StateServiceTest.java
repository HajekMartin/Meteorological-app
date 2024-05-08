package com.example.projekt.Service;

import com.example.projekt.DTO.StateDto;
import com.example.projekt.Entity.State;
import com.example.projekt.Repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StateServiceTest {

    @InjectMocks
    private StateService stateService;
    @Mock
    private StateRepository stateRepository;
    private State stateSuccessful;
    private State stateUnsuccessful;

    @BeforeEach
    void setup() {
        stateRepository = Mockito.mock(StateRepository.class);
        stateSuccessful = new State();
        stateSuccessful.setName("TEST");
        stateSuccessful.setId("TEST");
        stateUnsuccessful = new State();
        stateUnsuccessful.setName("TEST2");
        stateUnsuccessful.setId("TEST2");
        when(stateRepository.save(new State())).thenReturn(null);
        when(stateRepository.findStateById(stateSuccessful.getId().toString())).thenReturn(stateSuccessful);
        when(stateRepository.findStateById(stateUnsuccessful.getId())).thenReturn(null);
        stateService = new StateService(stateRepository);
    }

    @Test
    void createSuccessful() {
        State state = new State();
        state.setId("CZ");
        state.setName("CZ");
        boolean res = stateService.Create(state);
        assertEquals(true, res);
    }
    @Test
    void createUnsuccessful() {
        State state = new State();
        state.setId("CZ");
        // state.setName("CZ"); Chybí ID
        boolean res = stateService.Create(state);
        assertEquals(false, res);
    }

    @Test
    void createSuccessful1() {
        StateDto state = new StateDto();
        state.setId("CZ");
        state.setName("CZ");
        boolean res = stateService.Create(state);
        assertEquals(true, res);
    }

    @Test
    void createUnsuccessful1() {
        StateDto state = new StateDto();
        state.setId("CZ");
        // state.setName("CZ"); Chybí ID
        boolean res = stateService.Create(state);
        assertEquals(false, res);
    }

    @Test
    void readById() {
        State res = stateService.ReadById(stateSuccessful.getId());
        assertEquals(stateSuccessful, res);
    }

    @Test
    void updateSuccessful() {
        boolean res = stateService.Update(stateSuccessful);
        assertEquals(true, res);
    }

    @Test
    void updateUnsuccessful() {
        boolean res = stateService.Update(stateUnsuccessful);
        assertEquals(false, res);
    }

    @Test
    void deleteSuccessful() {
        boolean res = stateService.Delete(stateSuccessful.getId());
        assertEquals(true, res);
    }

    @Test
    void deleteUnsuccessful() {
        boolean res = stateService.Delete(stateUnsuccessful.getId());
        assertEquals(false, res);
    }
}