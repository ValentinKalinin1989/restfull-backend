package com.test.restfullbackend.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * model for user
 */
@Entity
@Table(name = "usr")
public class User {
    @Column(name = "name",
            nullable = false)
    @JsonView(Views.Name.class)
    private String name;

    @Id
    @Column(name = "login",
            nullable = false)
    @JsonView(Views.NameAndLogin.class)
    private String login;

    @Column(name = "password",
            nullable = false)
    @JsonView(Views.NameAndLoginAndPassw.class)
    private String password;

    @ManyToMany
    @JoinTable(name = "usr_role",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if(roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
