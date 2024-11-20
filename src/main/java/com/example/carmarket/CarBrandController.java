package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brands")
public class CarBrandController {

    @Autowired
    private CarBrandService carBrandService;

    @GetMapping
    public String listBrands(Model model) {
        model.addAttribute("brands", carBrandService.getAllBrands());
        return "brands";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("brand", new CarBrand());
        return "create_brand";
    }

    @PostMapping("/save")
    public String saveBrand(@ModelAttribute("brand") CarBrand brand) {
        carBrandService.saveBrand(brand);
        return "redirect:/brands";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("brand", carBrandService.getBrandById(id));
        return "edit_brand";
    }

    @PostMapping("/update/{id}")
    public String updateBrand(@PathVariable("id") Long id, @ModelAttribute("brand") CarBrand brand) {
        CarBrand existingBrand = carBrandService.getBrandById(id);
        if (existingBrand != null) {
            existingBrand.setBrandName(brand.getBrandName());
            carBrandService.saveBrand(existingBrand);
        }
        return "redirect:/brands";
    }

    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") Long id) {
        carBrandService.deleteBrand(id);
        return "redirect:/brands";
    }

    @GetMapping("/orders/new")
    public String createOrder(Model model) {
        model.addAttribute("brands", carBrandService.getAllBrands());
        return "order_create";
    }
}

