package com.example.projekt.Controller;

import com.example.projekt.DTO.StateDto;
import com.example.projekt.Entity.State;
import com.example.projekt.Service.LoggerService;
import com.example.projekt.Service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class StateApiReadController {

    @Autowired
    private StateService stateService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "StateApiReadController";

    public StateApiReadController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/api/state/findAll")
    public ResponseEntity<Iterable<StateDto>> getStates() {
        loggerService.LogControlller(controllerName, "/api/state/findAll", new ArrayList<String>(Arrays.asList()));
        Iterable<State> states = stateService.ReadAll();
        List<StateDto> result = new ArrayList<>();
        for (State state: states) {
            result.add(new StateDto(state));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/state/find")
    public ResponseEntity<StateDto> getState(@RequestParam String id) {
        loggerService.LogControlller(controllerName, "/api/state/find", new ArrayList<String>(Arrays.asList(id.toString())));
        State state = stateService.ReadById(id);
        if (state == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new StateDto(state));
    }
}
