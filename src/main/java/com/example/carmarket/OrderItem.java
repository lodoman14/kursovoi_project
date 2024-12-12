package com.example.carmarket;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "part_id", nullable = false)
    private CarPart carPart;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private int quantity;

    public OrderItem() {
    }

    public OrderItem(CarPart carPart, int quantity) {
        this.carPart = carPart;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarPart getCarPart() {
        return carPart;
    }

    public void setCarPart(CarPart carPart) {
        this.carPart = carPart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
