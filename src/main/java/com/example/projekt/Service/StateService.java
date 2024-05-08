package com.example.projekt.Service;

import com.example.projekt.Entity.State;
import com.example.projekt.DTO.StateDto;
import com.example.projekt.Repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;
    private final LoggerService loggerService = new LoggerService();
    private final String serviceName = "StateService";

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public boolean Create(State state) {
        loggerService.LogService(serviceName, "Create", new ArrayList<String>(Arrays.asList(state.toString())));
        if(state.getName() == null || state.getName().compareTo("") == 0) { // Pokud je prázdný jméno
            return false;
        } else if (state.getId() == null ||  state.getId().compareTo("") == 0) { // Pokud je id prázdný
            return false;
        } else if (stateRepository.findStateById(state.getId()) != null) {
            return false;
        }
        stateRepository.save(state);
        return true;
    }

    public boolean Create(StateDto stateDto) {
        loggerService.LogService(serviceName, "Create", new ArrayList<String>(Arrays.asList(stateDto.toString())));
        State state = new State();
        state.setId(stateDto.getId());
        state.setName(stateDto.getName());
        return this.Create(state);
    }

    public Iterable<State> ReadAll() {
        loggerService.LogService(serviceName, "ReadAll", new ArrayList<String>(Arrays.asList()));
        return stateRepository.findAll();
    }

    public State ReadById(String stateId) {
        loggerService.LogService(serviceName, "ReadById", new ArrayList<String>(Arrays.asList(stateId.toString())));
        return stateRepository.findStateById(stateId);
    }

    public boolean Update(State state) {
        loggerService.LogService(serviceName, "Update", new ArrayList<String>(Arrays.asList(state.toString())));
        if (state.getName() == null || state.getName().compareTo("") == 0) { // Pokud by byl název prázdný
            return false;
        } else if (stateRepository.findStateById(state.getId()) == null) { // Neexistuje tzn nemám co updatovat
            return false;
        }
        stateRepository.save(state);
        return true;
    }

    public boolean Update(StateDto stateDto) {
        loggerService.LogService(serviceName, "Update", new ArrayList<String>(Arrays.asList(stateDto.toString())));
        State state = new State();
        state.setId(stateDto.getId());
        state.setName(stateDto.getName());
        return this.Update(state);
    }

    public boolean Delete(String stateId) {
        loggerService.LogService(serviceName, "Delete", new ArrayList<String>(Arrays.asList(stateId.toString())));
        if (stateRepository.findStateById(stateId) == null) { // Neexistuje tzn nemám co mazat
            return false;
        }
        stateRepository.deleteById(stateId);
        return true;
    }
}
