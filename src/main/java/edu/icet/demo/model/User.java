package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String city;
    private int age;
    private String role;
    private Button btn;

    public User(String id, String name, String email, String password, String city, int age, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.age = age;
        this.role = role;
    }
}
