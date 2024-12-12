package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

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
    public String saveOrder(@ModelAttribute("order") Order order,
                            @RequestParam Map<String, String> allParams,
                            Model model) {
        logger.info("Received order: {}", order);
        logger.info("Received parameters: {}", allParams);

        List<OrderItem> items = new ArrayList<>();

        for (String key : allParams.keySet()) {
            if (key.startsWith("items[") && key.endsWith("].selected")) {
                String partIdKey = key.replace(".selected", ".carPartId");
                String quantityKey = key.replace(".selected", ".quantity");

                try {
                    Long partId = Long.parseLong(allParams.get(partIdKey));
                    Integer quantity = Integer.parseInt(allParams.get(quantityKey));

                    if (quantity > 0) {
                        CarPart part = carPartService.getPartById(partId);
                        if (part != null) {
                            items.add(new OrderItem(part, quantity));
                        }
                    }
                } catch (NumberFormatException e) {
                    model.addAttribute("error", "Некорректные данные. Проверьте ввод количества.");
                    return "create_order";
                }
            }
        }

        if (items.isEmpty()) {
            model.addAttribute("error", "Не выбрано ни одной запчасти.");
            return "create_order";
        }

        for (OrderItem item : items) {
            order.addItem(item);
        }

        try {
            orderService.saveOrder(order);
            logger.info("Order saved successfully: {}", order);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("parts", carPartService.getAllParts());
            return "create_order";
        }

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

    // Обновление статуса заказа
    @PostMapping("/updateStatus/{id}")
    public String updateOrderStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/orders";
    }
}
