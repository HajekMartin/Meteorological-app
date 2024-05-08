package com.example.projekt.Service;

import com.example.projekt.DTO.AverageValuesDto;
import com.example.projekt.Entity.Measurement;
import com.example.projekt.DTO.MeasurementDto;
import com.example.projekt.Repository.CityRepository;
import com.example.projekt.Repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private CityRepository cityRepository;
    private final LoggerService loggerService = new LoggerService();
    private final String serviceName = "MeasurementService";

    @Value("${expirationDays}")
    private Long expirationDays;

    public MeasurementService(MeasurementRepository measurementRepository, CityRepository cityRepository) {
        this.measurementRepository = measurementRepository;
        this.cityRepository = cityRepository;
    }

    public boolean Create(Measurement measurement) {
        loggerService.LogService(serviceName, "Create", new ArrayList<String>(Arrays.asList(measurement.toString())));
        measurement.setId(null); // Aby nemohl projít s ID
        if (measurement.getTemperature() == null || measurement.getTemperature() < -100 || measurement.getTemperature() > 100) { // Rádoby omezení hodnot
            return false;
        } else if (measurement.getCity() == null) { // Musí patřit k nějakému městu
            return false;
        } else if (measurement.getDate() == null || measurement.getDate() < 0 || measurementRepository.exitsMeasurementForCityByDate(measurement.getDate(), measurement.getCity().getId())) { // Aspoň ať je datum větší než 0
            return false;
        } else if (measurement.getRain() == null || measurement.getRain() < 0) { // Rádoby omezení hodnot
            return false;
        } else if (measurement.getWindSpeed() == null || measurement.getWindSpeed() < 0) { // Rádoby omezení hodnot
            return false;
        }
        measurementRepository.save(measurement);
        return true;
    }

    public boolean Create(MeasurementDto measurementDto) {
        loggerService.LogService(serviceName, "Create", new ArrayList<String>(Arrays.asList(measurementDto.toString())));
        Measurement measurement = new Measurement();
        measurement.setCity(cityRepository.findCityById(measurementDto.getCityId()));
        measurement.setTemperature(measurementDto.getTemperature());
        measurement.setDate(measurementDto.getDate());
        measurement.setWindSpeed(measurementDto.getWindSpeed());
        measurement.setRain(measurementDto.getRain());
        return this.Create(measurement);
    }

    public Iterable<Measurement> ReadAll() {
        loggerService.LogService(serviceName, "ReadAll", new ArrayList<String>(Arrays.asList()));
        return measurementRepository.findAll();
    }

    public Measurement ReadById(Long measurementId) {
        loggerService.LogService(serviceName, "ReadById", new ArrayList<String>(Arrays.asList(measurementId.toString())));
        return measurementRepository.findMeasurementById(measurementId);
    }

    public Iterable<Measurement> ReadForCityIdAndDuration(Long cityId, Long duration) {
        loggerService.LogService(serviceName, "ReadForCityIdAndDuration", new ArrayList<String>(Arrays.asList(cityId.toString(), duration.toString())));
        if (cityId == null || cityRepository.findCityById(cityId) == null) { // Pokud město neexistuje
            return null;
        }
        Iterable<Measurement> list = cityRepository.findCityById(cityId).getMeasurementListWithDuration(duration);
        return list;
    }

    public boolean Update(Measurement measurement) {
        loggerService.LogService(serviceName, "Update", new ArrayList<String>(Arrays.asList(measurement.toString())));
        if (measurement.getTemperature() == null || measurement.getTemperature() < -100 || measurement.getTemperature() > 100) { // Rádoby omezení hodnot
            return false;
        } else if (measurement.getCity() == null) { // Musí patřit k nějakému městu
            return false;
        } else if (measurement.getDate() == null || measurement.getDate() < 0) { // Aspoň ať je datum větší než 0
            return false;
        } else if (measurementRepository.findById(measurement.getId()) == null) { // Pokud neexistuje nemám co updatovat
            return false;
        } else if (measurement.getRain() == null || measurement.getRain() < 0) { // Rádoby omezení hodnot
            return false;
        } else if (measurement.getWindSpeed() == null || measurement.getWindSpeed() < 0) { // Rádoby omezení hodnot
            return false;
        }
        measurementRepository.save(measurement);
        return true;
    }

    public boolean Update(MeasurementDto measurementDto) {
        loggerService.LogService(serviceName, "Update", new ArrayList<String>(Arrays.asList(measurementDto.toString())));
        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());
        measurement.setDate(measurementDto.getDate());
        measurement.setTemperature(measurementDto.getTemperature());
        measurement.setCity(cityRepository.findCityById(measurementDto.getCityId()));
        measurement.setWindSpeed(measurementDto.getWindSpeed());
        measurement.setRain(measurementDto.getRain());
        return this.Update(measurement);
    }

    public boolean Delete(Long measurementId) {
        loggerService.LogService(serviceName, "Delete", new ArrayList<String>(Arrays.asList(measurementId.toString())));
        if (measurementRepository.findMeasurementById(measurementId) == null) { // Neexistuje tzn nemám co mazat
            return false;
        }
        measurementRepository.deleteById(measurementId);
        return true;
    }

    public void DeleteOldMeasurements() {
        loggerService.LogService(serviceName, "DeleteOldMeasurements", new ArrayList<String>(Arrays.asList()));
        Long expirationDate = System.currentTimeMillis()/1000 - 86400*expirationDays;
        measurementRepository.deleteExpiratedMeasurements(expirationDate);
    }

    public Measurement ActualMeasurement(Long cityId) {
        loggerService.LogService(serviceName, "ActualMeasurement", new ArrayList<String>(Arrays.asList(cityId.toString())));
        return measurementRepository.actualMeasurement(cityId);
    }

    public AverageValuesDto AverageValues(Long cityId, Long duration) {
        loggerService.LogService(serviceName, "AverageValues", new ArrayList<String>(Arrays.asList(cityId.toString(), duration.toString())));
        if (cityId == null || cityRepository.findCityById(cityId) == null) { // Pokud město neexistuje
            return null;
        } else if (duration == null || duration < 0) {
            return null;
        }
        Long actualTime = System.currentTimeMillis()/1000;
        Long startingTime = actualTime - duration;
        AverageValuesDto averageValuesDto = new AverageValuesDto();
        averageValuesDto.setTemperature(measurementRepository.averageTemperatureForCity(startingTime, cityId));
        averageValuesDto.setWindSpeed(measurementRepository.averageWindSpeedForCity(startingTime, cityId));
        averageValuesDto.setRain(measurementRepository.averageRainForCity(startingTime, cityId));
        return averageValuesDto;
    }
}
