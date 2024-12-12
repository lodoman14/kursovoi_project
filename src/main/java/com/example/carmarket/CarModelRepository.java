package com.example.carmarket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    List<CarModel> findByBrand(CarBrand brand);
}
