package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CarPartService carPartService;

    // Список всех заказов
    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    // Форма для создания нового заказа
    @GetMapping("/new")
    public String createOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("parts", carPartService.getAllParts()); // Передаем список всех запчастей
        return "create_order";
    }

    // Сохранение нового заказа
    @PostMapping("/save")
    public String saveOrder(@ModelAttribute("order") Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    // Просмотр деталей заказа
    @GetMapping("/{id}")
    public String viewOrder(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/orders";
        }
        model.addAttribute("order", order);
        return "view_order";
    }
}

