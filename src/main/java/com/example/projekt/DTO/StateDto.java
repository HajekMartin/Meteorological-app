package com.example.projekt.DTO;

import com.example.projekt.Entity.State;
import com.fasterxml.jackson.annotation.JsonCreator;

public class StateDto {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateDto(State entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    @JsonCreator
    public StateDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public StateDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StateDto)) {
            return false;
        }
        StateDto s = (StateDto) o;
        return s.getName().compareTo(getName()) == 0 && s.getId().compareTo(getName()) == 0;
    }

}
