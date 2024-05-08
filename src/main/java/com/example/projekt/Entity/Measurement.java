package com.example.projekt.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MEASUREMENT")
public class Measurement implements Comparable<Measurement> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="MEASUREMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="CITY_ID")
    private City city;

    @Column(name="DATE")
    private Long date;

    @Column(name="TEMPERATURE")
    private Double temperature;

    @Column(name="WINDSPEED")
    private Double windSpeed;

    @Column(name="RAIN")
    private Double rain;

    public Long getId() { return this.id; }
    public void setId(Long id) {this.id = id; }

    public City getCity() { return this.city; }
    public void setCity(City city) { this.city = city; }

    public Long getDate() { return this.date; } // V sec formátu musí být
    public void setDate(Long date) {
        this.date = date;
    }

    public Double getTemperature() { return this.temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

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

    // Abych to aspoň u výpisu mohl seřadit podle data
    @Override
    public int compareTo(Measurement o) {
        if (this.getDate() == null || o.getDate() == null) {
            return 0;
        }
        return  this.getDate().compareTo(o.getDate());
    }
}
