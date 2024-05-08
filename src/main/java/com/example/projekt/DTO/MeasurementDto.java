package com.example.projekt.DTO;

import com.example.projekt.Entity.Measurement;
import com.fasterxml.jackson.annotation.JsonCreator;

public class MeasurementDto {
    private Long id;
    private Long cityId;
    private Long date;
    private Double temperature;
    private Double rain;
    private double windSpeed;

    public Long getId() {
        return id;
    }

    public Long getCityId() {
        return cityId;
    }

    public Long getDate() {
        return date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getRain() {
        return rain;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public MeasurementDto(Measurement entity) {
        this.id = entity.getId();
        this.cityId = entity.getCity().getId();
        this.date = entity.getDate();
        this.temperature = entity.getTemperature();
        this.windSpeed = entity.getWindSpeed();
        this.rain = entity.getRain();
    }

    @JsonCreator
    public MeasurementDto(Long id, Long cityId, Long date, Double temperature, Double rain, Double windSpeed) {
        this.id = id;
        this.cityId = cityId;
        this.date = date;
        this.temperature = temperature;
        this.rain = rain;
        this.windSpeed = windSpeed;
    }

    public MeasurementDto() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MeasurementDto)) {
            return false;
        }
        MeasurementDto s = (MeasurementDto) o;
        return s.getId() == this.getId() && s.getCityId() == getCityId() && s.getDate() == getDate() && s.getTemperature() == getTemperature() && s.getRain() == getRain() && s.getWindSpeed() == getWindSpeed();
    }
}
