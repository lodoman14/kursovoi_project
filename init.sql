-- Сначала создаем таблицу car_brands
CREATE TABLE IF NOT EXISTS car_brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_name VARCHAR(255) NOT NULL
);

-- Создаем таблицу car_models
CREATE TABLE IF NOT EXISTS car_models (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model_name VARCHAR(255) NOT NULL,
    brand_id BIGINT,
    CONSTRAINT FK_brand FOREIGN KEY (brand_id) REFERENCES car_brands(id)
);

-- Создаем таблицу car_parts
CREATE TABLE IF NOT EXISTS car_parts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    part_name VARCHAR(255) NOT NULL,
    article_number VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    model_id BIGINT,
    CONSTRAINT FK_model FOREIGN KEY (model_id) REFERENCES car_models(id)
);

-- Создаем таблицу order_status для хранения статусов заказов
CREATE TABLE IF NOT EXISTS order_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(255) NOT NULL
);

-- Заполняем таблицу order_status начальными значениями
INSERT INTO order_status (status_name) VALUES
('Создан счет'),
('Оплата по счету получена'),
('Товар собран и готов к отгрузке'),
('Доставка до клиента'),
('Доставка осуществлена');

-- Создаем таблицу orders
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    status_id BIGINT DEFAULT 1,
    CONSTRAINT FK_order_status FOREIGN KEY (status_id) REFERENCES order_status(id)
);

-- Создаем таблицу order_items
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    part_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT FK_part FOREIGN KEY (part_id) REFERENCES car_parts(id),
    CONSTRAINT FK_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
