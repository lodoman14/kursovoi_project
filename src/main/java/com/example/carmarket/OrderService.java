package com.example.carmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CarPartService carPartService;

    public Order saveOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            CarPart part = item.getCarPart();
            if (part.getQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Недостаточно товара на складе: " + part.getPartName());
            }
            part.setQuantity(part.getQuantity() - item.getQuantity());
            carPartService.savePart(part);
        }
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}

