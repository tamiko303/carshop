package com.artocons.carshop.persistence.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "color")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "colors")
    private final Set<Car> cars = new HashSet<>();

    public  Color(){
    }

    public Color(String name) { this.name = name; }
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getColorName() { return name; }

    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (!this.getClass().equals(obj.getClass()))
            return false;
        Color obj2 = (Color)obj;
        if ((this.id == obj2.getId())
                && (this.name.equals(
                obj2.getColorName()))) {
            return true;
        }
        return false;
    }
    public int hashCode()
    {
        int tmp = 0;
        tmp = (id + name).hashCode();
        return tmp;
    }

    @Override
    public String toString() {
        return this.name;
    }

}