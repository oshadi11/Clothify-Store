package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Supplier {
    private String id;
    private String name;
    private String email;
    private String company;
    private Button btn;

    public Supplier(String id, String name, String email, String company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.company = company;
    }
}
