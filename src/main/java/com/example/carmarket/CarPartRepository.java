package com.example.carmarket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarPartRepository extends JpaRepository<CarPart, Long> {
    List<CarPart> findByModel(CarModel model);
}
