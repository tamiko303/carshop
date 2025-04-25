package com.artocons.carshop.persistence.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "role_name")
    @NotNull
    private String roleName;

    @ManyToMany(mappedBy = "userRoles")
    private final Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (!this.getClass().equals(obj.getClass()))
            return false;
        UserRole obj2 = (UserRole)obj;
        if ((this.roleId == obj2.getRoleId())
                && (this.roleName.equals(
                obj2.getRoleName()))) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int tmp = 0;
        tmp = (roleId + roleName).hashCode();
        return tmp;
    }

    @Override
    public String toString() {
        return this.roleName;
    }
}
