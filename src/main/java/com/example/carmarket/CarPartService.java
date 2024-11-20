package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarPartService {

    @Autowired
    private CarPartRepository carPartRepository;

    // Получение всех запчастей для конкретной модели
    public List<CarPart> getPartsByModel(CarModel model) {
        return carPartRepository.findByCarModel(model);
    }

    // Сохранение запчасти
    public void savePart(CarPart part) {
        carPartRepository.save(part);
    }

    // Получение запчасти по ID
    public CarPart getPartById(Long id) {
        return carPartRepository.findById(id).orElse(null);
    }

    // Удаление запчасти по ID
    public void deletePart(Long id) {
        carPartRepository.deleteById(id);
    }

    // Получение всех запчастей
    public List<CarPart> getAllParts() {
        return carPartRepository.findAll();
    }
}

