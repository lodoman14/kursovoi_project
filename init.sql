-- Создаем таблицу car_brands
CREATE TABLE IF NOT EXISTS car_brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_name VARCHAR(255) NOT NULL
);

-- Создаем таблицу car_models, которая ссылается на car_brands
CREATE TABLE IF NOT EXISTS car_models (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model_name VARCHAR(255) NOT NULL,
    brand_id BIGINT,
    CONSTRAINT FK_brand FOREIGN KEY (brand_id) REFERENCES car_brands(id)
);

-- Создаем таблицу car_parts, которая ссылается на car_models
CREATE TABLE IF NOT EXISTS car_parts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    article_number VARCHAR(255) NOT NULL,
    part_name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    model_id BIGINT,
    CONSTRAINT FK_model FOREIGN KEY (model_id) REFERENCES car_models(id)
);

-- Создаем таблицу orders
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255) NOT NULL
);

-- Создаем таблицу order_items, которая связывает orders и car_parts
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    part_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (part_id) REFERENCES car_parts (id)
);

-- Создаем таблицу invoices
CREATE TABLE IF NOT EXISTS invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Создаем таблицу invoice_parts, которая связывает invoices и car_parts
CREATE TABLE IF NOT EXISTS invoice_parts (
    invoice_id BIGINT NOT NULL,
    part_id BIGINT NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES invoices (id) ON DELETE CASCADE,
    FOREIGN KEY (part_id) REFERENCES car_parts (id)
);
