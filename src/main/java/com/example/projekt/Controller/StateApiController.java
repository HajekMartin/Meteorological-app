package com.example.projekt.Controller;

import com.example.projekt.DTO.StateDto;
import com.example.projekt.Service.LoggerService;
import com.example.projekt.Service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@ConditionalOnProperty(name = "readmode", havingValue = "false")
public class StateApiController {

    @Autowired
    private StateService stateService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "StateApiController";

    public StateApiController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/api/state/add")
    public ResponseEntity<String> insertState(@RequestBody StateDto stateDto) {
        loggerService.LogControlller(controllerName, "/api/state/add", new ArrayList<String>(Arrays.asList(stateDto.toString())));
        if (stateService.Create(stateDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/api/state/delete")
    public ResponseEntity<String> deleteState(@RequestParam String id) {
        loggerService.LogControlller(controllerName, "/api/state/delete", new ArrayList<String>(Arrays.asList(id.toString())));
        if (stateService.Delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/state/edit")
    public ResponseEntity<String> updateState(@RequestBody StateDto state) {
        loggerService.LogControlller(controllerName, "/api/state/edit", new ArrayList<String>(Arrays.asList(state.toString())));
        if (stateService.Update(state)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
