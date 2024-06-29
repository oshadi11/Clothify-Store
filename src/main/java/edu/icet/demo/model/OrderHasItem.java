package edu.icet.demo.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderHasItem {
    private Integer id;
    private String orderId;
    private String productId;
    private String productName;
    private int qty;
    private double amount;
}