package com.example.projekt.DTO;

public class AverageValuesDto {

    private Double temperature;
    private Double windSpeed;
    private Double rain;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getRain() {
        return rain;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }
}
