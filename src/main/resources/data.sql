
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
    ('Item 1', 'https://example.com/item1.jpg', 9.99, 10),
    ('Item 2', 'https://example.com/item2.jpg', 19.99, 5),
    ('Item 3', 'https://example.com/item3.jpg', 29.99, 3);