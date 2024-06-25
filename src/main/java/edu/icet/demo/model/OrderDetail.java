package edu.icet.demo.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private String id;
    private String cusId;
    private String paymentMethod;
    private Date date;
    private Double amount;
    private String empId;
}