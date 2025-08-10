
CREATE TABLE items (
    id INT AUTO_INCREMENT ,
    title VARCHAR(50) UNIQUE NOT NULL,
    photo VARCHAR(1150) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL,
    PRIMARY KEY (id)
    );

    CREATE TABLE users (
    id INT AUTO_INCREMENT ,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(500) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(50) NOT NULL,
    address VARCHAR(500) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'USER',
    PRIMARY KEY (id)
    );

    CREATE TABLE favorites (
    id INT AUTO_INCREMENT ,
    username VARCHAR(50) NOT NULL,
    item_id int NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (item_id) REFERENCES items(id),
    PRIMARY KEY (id)
    );

    CREATE TABLE orders (
    id INT AUTO_INCREMENT ,
    username VARCHAR(50) NOT NULL,
    date_order DATE NOT NULL DEFAULT CURRENT_DATE,
    shipping_address VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'TEMP',
    total_price DOUBLE ,
    FOREIGN KEY (username) REFERENCES users(username),
    PRIMARY KEY(id)
    );

    CREATE TABLE order_items (
    id INT AUTO_INCREMENT ,
    order_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (item_id) REFERENCES items(id),
    PRIMARY KEY (id)
    );

    INSERT INTO users (username, password, first_name, last_name, email, phone, address, role) VALUES
    ('admin', '$2a$10$nb/SCAWFXf7y/Tk45EOs7umtkF2K69jjl2uuH4ghB41P6/h.m4zW2', 'admin', 'admin', '5Zm4Z@example.com', '1234567890', '123 Main St','USER'),
    ('yar','$2a$10$58rp5kaUmrqr6SJQo1X3Semte6mAxMcktNNeOekY0S/xS1MpdeRTq', 'yar', 'yar', 'yar@yar', '1234567890', '123 Main St' ,'USER');

INSERT INTO items (title, photo, price, stock) VALUES
('Dior Sauvage', 'https://www.sephora.com/productimages/sku/s2038123-main-zoom.jpg?imwidth=460', 399.90, 15),
('Bleu de Chanel', 'https://www.sephora.com/productimages/sku/s1695972-main-zoom.jpg?imwidth=460', 429.00, 10),
('Armani Acqua di Gio', 'https://www.sephora.com/productimages/sku/s2848695-main-zoom.jpg?imwidth=460', 349.90, 20),
('Versace Eros', 'https://www.sephora.com/productimages/sku/s2371581-main-zoom.jpg?imwidth=1224', 319.00, 18),
('Paco Rabanne 1 Million', 'https://www.sephora.com/productimages/sku/s2786531-main-zoom.jpg?imwidth=460', 299.90, 25),
('Tom Ford Noir', 'https://www.sephora.com/productimages/sku/s1706423-main-zoom.jpg?imwidth=460', 549.00, 5),
('YSL La Nuit de l’Homme', 'https://www.sephora.com/productimages/sku/s1200716-main-zoom.jpg?imwidth=460', 389.00, 12),
('Burberry Hero', 'https://www.sephora.com/productimages/sku/s2841062-main-zoom.jpg?imwidth=460', 279.00, 20),
('Calvin Klein Euphoria', 'https://www.sephora.com/productimages/sku/s2704062-main-zoom.jpg?imwidth=460', 199.90, 30),
('Tom Ford ', 'https://www.sephora.com/productimages/sku/s1449289-main-zoom.jpg?imwidth=460', 259.00, 16),
('Jean Paul Gaultier ', 'https://www.sephora.com/productimages/sku/s2822476-main-zoom.jpg?imwidth=460', 329.90, 9),
('Dolce & Gabbana Light Blue', 'https://www.sephora.com/productimages/sku/s2863314-main-zoom.jpg?imwidth=460', 289.00, 14);
