package com.example.carmarket;

import org.springframework.data.jpa.repository.JpaRepository;

// Интерфейс репозитория для работы с таблицей order_status
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
