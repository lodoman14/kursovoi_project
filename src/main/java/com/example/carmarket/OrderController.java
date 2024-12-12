package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CarPartService carPartService;

    @GetMapping("/new")
    public String createOrderForm(Model model) {
        model.addAttribute("parts", carPartService.getAllParts());
        model.addAttribute("order", new Order());
        return "create_order";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute("order") Order order,
                            @RequestParam Map<String, String> allParams,
                            Model model) {
        List<OrderItem> items = new ArrayList<>();

        for (String key : allParams.keySet()) {
            if (key.startsWith("items[") && key.endsWith("].selected")) {
                String baseKey = key.substring(0, key.indexOf(".selected"));
                String partIdKey = baseKey + ".carPartId";
                String quantityKey = baseKey + ".quantity";

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
            model.addAttribute("error", "Не выбраны товары для заказа.");
            return "create_order";
        }

        order.setItems(items);
        orderService.saveOrder(order);

        return "redirect:/";
    }
}

