package com.example.projekt.Service;

import com.example.projekt.Entity.City;
import com.example.projekt.DTO.CityDto;
import com.example.projekt.Repository.CityRepository;
import com.example.projekt.Repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private OpenWeatherService openWeatherService;
    @Autowired
    private StateRepository stateRepository;
    private final LoggerService loggerService = new LoggerService();
    private final String serviceName = "CityService";

    public CityService(CityRepository cityRepository, StateRepository stateRepository, OpenWeatherService openWeatherService) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.openWeatherService = openWeatherService;
    }

    public boolean Create(City city) {
        loggerService.LogService(serviceName, "Create", new ArrayList<String>(Arrays.asList(city.toString())));
        // Jakoby validace
        if (city.getName() == null || city.getName().compareTo("") == 0) { // Pokud je název prázdný
            return false;
        } else if (city.getState() == null) { // Pokud je stát prázdný
            return false;
        } else if (city.getId() == null || city.getId() < 1) { // Pokud je ID prázdný
            return false;
        } else if (cityRepository.findCityById(city.getId()) != null) { // Pokud město s takovýmto ID mám již
            return false;
        } else if (!openWeatherService.TestCityId(city.getId())) { // Pokud není validní OpenWeatherId
            return false;
        }
        cityRepository.save(city);
        return true;
    }

    public boolean Create(CityDto cityDto) {
        loggerService.LogService(serviceName, "Create", new ArrayList<String>(Arrays.asList(cityDto.toString())));
        City city = new City();
        city.setState(stateRepository.findStateById(cityDto.getStateId()));
        city.setId(cityDto.getId());
        city.setName(cityDto.getName());
        return this.Create(city);
    }

    public Iterable<City> ReadAll() {
        loggerService.LogService(serviceName, "ReadAll", new ArrayList<String>(Arrays.asList()));
        return cityRepository.findAll();
    }

    public City ReadById(Long cityId) {
        loggerService.LogService(serviceName, "ReadById", new ArrayList<String>(Arrays.asList(cityId.toString())));
        return cityRepository.findCityById(cityId); // Pokud neexistuje vrací null
    }

    public boolean Update(City city) {
        loggerService.LogService(serviceName, "Update", new ArrayList<String>(Arrays.asList(city.toString())));
        if (city.getName() == null || city.getName().compareTo("") == 0) { // Pokud by byl název prázdný
            return false;
        } else if (city.getState() == null) { // Pokud je stát prázdný
            return false;
        } else if (cityRepository.findCityById(city.getId()) == null) { // Neexistuje tzn nemám co updatovat
            return false;
        }
        cityRepository.save(city);
        return true;
    }

    public boolean Update(CityDto cityDto) {
        loggerService.LogService(serviceName, "Update", new ArrayList<String>(Arrays.asList(cityDto.toString())));
        City city = new City();
        city.setId(cityDto.getId());
        city.setName(cityDto.getName());
        city.setState(stateRepository.findStateById(cityDto.getStateId()));
        return this.Update(city);
    }

    public boolean Delete(Long cityId) {
        loggerService.LogService(serviceName, "Delete", new ArrayList<String>(Arrays.asList(cityId.toString())));
        if (cityRepository.findCityById(cityId) == null) { // Neexistuje tzn nemám co mazat
            return false;
        }
        cityRepository.deleteById(cityId);
        return true;
    }

    public Long CityIdWithOldestData() {
        loggerService.LogService(serviceName, "CityIdWithOldestData", new ArrayList<String>(Arrays.asList()));
        return cityRepository.findCityIdWithOldestData();
    }
}
