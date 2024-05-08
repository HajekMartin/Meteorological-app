package com.example.projekt.DTO;

import com.example.projekt.Entity.City;
import com.fasterxml.jackson.annotation.JsonCreator;

public class CityDto {
    private Long id;
    private String stateId;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityDto(City entity) {
        this.id = entity.getId();
        this.stateId = entity.getState().getId();
        this.name = entity.getName();
    }

    @JsonCreator
    public CityDto(Long id, String stateId, String name) {
        this.stateId = stateId;
        this.name = name;
        this.id = id;
    }

    public CityDto() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CityDto)) {
            return false;
        }
        CityDto s = (CityDto) o;
        return s.getName().compareTo(getName()) == 0 && s.getId() == getId() && s.getStateId().compareTo(getStateId()) == 0;
    }
}
