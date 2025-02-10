package com.artocons.carshop.persistence.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long color_id;

    private String name;

    @ManyToMany(mappedBy = "colors")
    private Set<Car> cars = new HashSet<>();

    public Color() {}

    public Long getId() { return color_id; }

    public Color(String name) { this.name = name; }

    public void setId(Long id) { this.color_id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Set<Car> getCars() { return cars; }

    public void setCars(Set<Car> cars) { this.cars = cars; }
}