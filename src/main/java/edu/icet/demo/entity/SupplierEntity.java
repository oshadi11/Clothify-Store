package edu.icet.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Entity(name = "supplier")
@Table(name = "supplier")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String company;
}
