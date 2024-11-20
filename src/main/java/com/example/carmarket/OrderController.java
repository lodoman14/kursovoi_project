package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/new")
    public String createOrder(Model model) {
        model.addAttribute("brands", carBrandService.getAllBrands());
        model.addAttribute("order", new Order());
        return "order_create";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute("order") Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }
}

