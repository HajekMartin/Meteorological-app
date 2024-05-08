package com.example.projekt.Controller;

import com.example.projekt.DTO.CityDto;
import com.example.projekt.Service.CityService;
import com.example.projekt.Service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@ConditionalOnProperty(name = "readmode", havingValue = "false")
public class CityApiController {

    @Autowired
    private CityService cityService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "CityApiController";

    public CityApiController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/api/city/add")
    public ResponseEntity<String> insertCity(@RequestBody CityDto city) {
        loggerService.LogControlller(controllerName, "/api/city/add", new ArrayList<String>(Arrays.asList(city.toString())));
        if (cityService.Create(city)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/api/city/delete")
    public ResponseEntity<String> deleteCity(@RequestParam Long id) {
        loggerService.LogControlller(controllerName, "/api/city/delete", new ArrayList<String>(Arrays.asList(id.toString())));
        if (cityService.Delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/city/edit")
    public ResponseEntity<String> updateCity(@RequestBody CityDto city) {
        loggerService.LogControlller(controllerName, "/api/city/edit", new ArrayList<String>(Arrays.asList(city.toString())));
        if (cityService.Update(city)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
