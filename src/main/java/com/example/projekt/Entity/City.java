package com.example.projekt.Entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "CITY")
public class City {
    @Id
    @Column(name="CITY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="STATE_ID")
    private State state;

    @Column(name="NAME")
    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY) // Kouzelný řádek co zařídí aby se všechny měření smazaly pro toto město
    private List<Measurement> measurementList;

    public Long getId() { return this.id; }
    public void setId(Long id) {this.id = id; }

    public State getState() { return this.state; }
    public void setState(State state) { this.state = state; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public List<Measurement> getMeasurementList() { return this.measurementList; }
    public void setMeasurementList(List<Measurement> measurementList) { this.measurementList = measurementList; } // Radši nebudu měnit nevím zda se to nějak nepoužívá v tom frameworku
    public List<Measurement> getMeasurementListWithDuration(Long durationInSeconds) { //Long durationInSeconds v sekundách
        final long actualUnixTime = System.currentTimeMillis()/1000 - durationInSeconds; // dělení 100 abych měl sekundový unix čas
        return measurementList.stream().filter(m -> m.getDate() > actualUnixTime).sorted().collect(Collectors.toList());
    }
}
