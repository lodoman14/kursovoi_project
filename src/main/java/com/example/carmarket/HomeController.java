package com.example.carmarket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to the Car Market!");
        return "home";  // Это будет ссылаться на шаблон home.html
    }

    @GetMapping("/error")
    public String errorPage(Model model) {
        model.addAttribute("message", "Произошла ошибка. Пожалуйста, попробуйте позже.");
        return "error"; // Это будет ссылаться на шаблон error.html
    }
}

