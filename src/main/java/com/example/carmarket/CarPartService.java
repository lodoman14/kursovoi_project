package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarPartService {

    @Autowired
    private CarPartRepository carPartRepository;

    public List<CarPart> getPartsByModel(CarModel model) {
        return carPartRepository.findByCarModel(model);
    }

    public void savePart(CarPart part) {
        carPartRepository.save(part);
    }

    public CarPart getPartById(Long id) {
        return carPartRepository.findById(id).orElse(null);
    }

    public List<CarPart> getAllParts() {
        return carPartRepository.findAll();
    }

    public void deletePart(Long id) {
        carPartRepository.deleteById(id);
    }
}

