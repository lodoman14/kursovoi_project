package com.example.carmarket;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @ManyToMany
    @JoinTable(
        name = "invoice_parts",
        joinColumns = @JoinColumn(name = "invoice_id"),
        inverseJoinColumns = @JoinColumn(name = "part_id")
    )
    private List<CarPart> parts;

    public Invoice() {}

    public Invoice(String fullName, String email, String phone, String address, List<CarPart> parts) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.parts = parts;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CarPart> getParts() {
        return parts;
    }

    public void setParts(List<CarPart> parts) {
        this.parts = parts;
    }
}
