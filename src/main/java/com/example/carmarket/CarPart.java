package com.example.carmarket;

import jakarta.persistence.*;

@Entity
@Table(name = "car_parts")
public class CarPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String partName;

    @Column(nullable = false)
    private String articleNumber; // Артикул

    @Column(nullable = false)
    private Double price; // Цена

    @Column(nullable = false)
    private Integer quantity; // Остатки на складе

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel carModel;

    public CarPart() {}

    public CarPart(String partName, String articleNumber, Double price, Integer quantity, CarModel carModel) {
        this.partName = partName;
        this.articleNumber = articleNumber;
        this.price = price;
        this.quantity = quantity;
        this.carModel = carModel;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }
}

