package com.example.projekt.Controller;

import com.example.projekt.DTO.CityDto;
import com.example.projekt.Entity.City;
import com.example.projekt.Service.CityService;
import com.example.projekt.Service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CityApiReadController {

    @Autowired
    private CityService cityService;
    private final LoggerService loggerService = new LoggerService();
    private static final String controllerName = "CityApiReadController";

    public CityApiReadController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/api/city/findAll")
    public ResponseEntity<Iterable<CityDto>> getCities() {
        loggerService.LogControlller(controllerName, "/api/city/findAll", new ArrayList<String>(Arrays.asList()));
        Iterable<City> cities = cityService.ReadAll();
        List<CityDto> result = new ArrayList<>();
        for (City city: cities) {
            result.add(new CityDto(city));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/city/find")
    public ResponseEntity<CityDto> getCity(@RequestParam Long id) {
        loggerService.LogControlller(controllerName, "/api/city/find", new ArrayList<String>(Arrays.asList(id.toString())));
        City city = cityService.ReadById(id);
        if (city == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new CityDto(city));
    }
}
