package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/parts")
public class CarPartController {

    @Autowired
    private CarPartService carPartService;

    @Autowired
    private CarModelService carModelService;

    @GetMapping
    public String listParts(Model model) {
        model.addAttribute("parts", carPartService.getAllParts());
        return "parts_list";
    }

    @GetMapping("/model/{id}")
    public String listPartsByModel(@PathVariable("id") Long modelId, Model model) {
        CarModel modelEntity = carModelService.getModelById(modelId);
        if (modelEntity != null) {
            model.addAttribute("parts", carPartService.getPartsByModel(modelEntity));
        } else {
            model.addAttribute("error", "Модель не найдена");
        }
        return "parts_list";
    }

    @GetMapping("/new")
    public String createPartForm(Model model) {
        model.addAttribute("part", new CarPart());
        model.addAttribute("models", carModelService.getAllModels());
        return "create_part";
    }

    @PostMapping("/save")
    public String savePart(@ModelAttribute("part") CarPart part, Model model) {
        try {
            carPartService.savePart(part);
            return "redirect:/parts";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при сохранении запчасти");
            return "create_part";
        }
    }

    @GetMapping("/edit/{id}")
    public String editPartForm(@PathVariable("id") Long partId, Model model) {
        CarPart part = carPartService.getPartById(partId);
        if (part != null) {
            model.addAttribute("part", part);
            model.addAttribute("models", carModelService.getAllModels());
            return "edit_part";
        } else {
            model.addAttribute("error", "Запчасть не найдена");
            return "redirect:/parts";
        }
    }

    @PostMapping("/update")
    public String updatePart(@ModelAttribute("part") CarPart part, Model model) {
        try {
            carPartService.savePart(part);
            return "redirect:/parts";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при обновлении запчасти");
            return "edit_part";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePart(@PathVariable("id") Long partId, Model model) {
        try {
            carPartService.deletePart(partId);
            return "redirect:/parts";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при удалении запчасти");
            return "redirect:/parts";
        }
    }
}
