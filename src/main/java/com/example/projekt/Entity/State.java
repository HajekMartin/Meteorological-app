package com.example.projekt.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "STATE")
public class State {
    @Id
    @Column(name="STATE_ID")
    private String id;

    @Column(name="NAME")
    private String name;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY) // Kouzelný řádek co zařídí aby se všechny města smazaly pro tento stát
    private List<City> cityList;

    public String getId() { return this.id; }
    public void setId(String id) {this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public List<City> getCityList() { return this.cityList; }
    public void setCityList(List<City> cityList) { this.cityList = cityList; }
}
