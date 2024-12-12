package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brands/{brandId}/models/{modelId}/parts")
public class CarPartController {

    @Autowired
    private CarPartService carPartService;

    @Autowired
    private CarModelService carModelService;

    // Метод для отображения списка запчастей для модели
    @GetMapping
    public String listParts(@PathVariable("modelId") Long modelId, Model model) {
        CarModel carModel = carModelService.getModelById(modelId);
        model.addAttribute("parts", carPartService.getPartsByModel(carModel));
        model.addAttribute("carModel", carModel); // Передаем объект модели в шаблон
        return "parts"; // Шаблон parts.html
    }

    // Метод для отображения формы создания новой запчасти
    @GetMapping("/new")
    public String showCreateForm(@PathVariable("modelId") Long modelId, Model model) {
        CarModel carModel = carModelService.getModelById(modelId);
        model.addAttribute("part", new CarPart());
        model.addAttribute("carModel", carModel);
        return "create_part"; // Шаблон create_part.html
    }

    // Метод для сохранения новой запчасти
    @PostMapping("/save")
    public String savePart(@PathVariable("modelId") Long modelId, @ModelAttribute("part") CarPart part) {
        CarModel carModel = carModelService.getModelById(modelId);
        part.setCarModel(carModel);
        carPartService.savePart(part);
        return "redirect:/brands/" + carModel.getCarBrand().getId() + "/models/" + modelId + "/parts";
    }

    // Метод для отображения формы редактирования запчасти
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("modelId") Long modelId, @PathVariable("id") Long id, Model model) {
        CarPart part = carPartService.getPartById(id);
        CarModel carModel = carModelService.getModelById(modelId);
        model.addAttribute("part", part);
        model.addAttribute("carModel", carModel); // Передаем объект модели
        return "edit_part"; // Шаблон edit_part.html
    }

    // Метод для обновления запчасти
    @PostMapping("/update/{id}")
    public String updatePart(@PathVariable("modelId") Long modelId, @PathVariable("id") Long id, @ModelAttribute("part") CarPart part) {
        CarPart existingPart = carPartService.getPartById(id);
        if (existingPart != null) {
            existingPart.setPartName(part.getPartName()); // Обновляем наименование
            existingPart.setArticleNumber(part.getArticleNumber()); // Обновляем артикул
            existingPart.setPrice(part.getPrice()); // Обновляем цену
            existingPart.setQuantity(part.getQuantity()); // Обновляем остаток
            carPartService.savePart(existingPart);
        }
        return "redirect:/brands/" + existingPart.getCarModel().getCarBrand().getId() + "/models/" + modelId + "/parts";
    }

    // Метод для удаления запчасти
    @GetMapping("/delete/{id}")
    public String deletePart(@PathVariable("modelId") Long modelId, @PathVariable("id") Long id) {
        carPartService.deletePart(id);
        return "redirect:/brands/" + modelId + "/models/" + modelId + "/parts";
    }
}

