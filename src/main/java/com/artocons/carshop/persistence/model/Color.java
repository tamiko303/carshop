package com.artocons.carshop.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "color")
@NoArgsConstructor
@AllArgsConstructor
public class Color {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String name;

    @ManyToMany(mappedBy = "colors")
    private final Set<Car> cars = new HashSet<>();

    public Color(String name) { this.name = name; }

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