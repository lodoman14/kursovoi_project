package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/models")
public class CarModelController {

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private CarBrandService carBrandService;

    @GetMapping
    public String listModels(Model model) {
        model.addAttribute("models", carModelService.getAllModels());
        return "models_list";
    }

    @GetMapping("/brand/{id}")
    public String listModelsByBrand(@PathVariable("id") Long brandId, Model model) {
        CarBrand brand = carBrandService.getBrandById(brandId);
        if (brand != null) {
            model.addAttribute("models", carModelService.getModelsByBrand(brand));
        } else {
            model.addAttribute("error", "Марка не найдена");
        }
        return "models_list";
    }

    @GetMapping("/new")
    public String createModelForm(Model model) {
        model.addAttribute("model", new CarModel());
        model.addAttribute("brands", carBrandService.getAllBrands());
        return "create_model";
    }

    @PostMapping("/save")
    public String saveModel(@ModelAttribute("model") CarModel carModel, Model model) {
        try {
            carModelService.saveModel(carModel);
            return "redirect:/models";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при сохранении модели");
            return "create_model";
        }
    }

    @GetMapping("/edit/{id}")
    public String editModelForm(@PathVariable("id") Long modelId, Model model) {
        CarModel carModel = carModelService.getModelById(modelId);
        if (carModel != null) {
            model.addAttribute("model", carModel);
            model.addAttribute("brands", carBrandService.getAllBrands());
            return "edit_model";
        } else {
            model.addAttribute("error", "Модель не найдена");
            return "redirect:/models";
        }
    }

    @PostMapping("/update")
    public String updateModel(@ModelAttribute("model") CarModel carModel, Model model) {
        try {
            carModelService.saveModel(carModel);
            return "redirect:/models";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при обновлении модели");
            return "edit_model";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteModel(@PathVariable("id") Long modelId, Model model) {
        try {
            carModelService.deleteModel(modelId);
            return "redirect:/models";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при удалении модели");
            return "redirect:/models";
        }
    }
}
