INSERT INTO Pizzas (name, price) VALUES ('Маргарита', 600);
INSERT INTO Pizzas (name, price) VALUES ('Италиано', 650);

INSERT INTO Ingredients (name) VALUES ('Сосиски');
INSERT INTO Ingredients (name) VALUES ('Колбаса');
INSERT INTO Ingredients (name) VALUES ('Томаты');
INSERT INTO Ingredients (name) VALUES ('Кетчуп');
INSERT INTO Ingredients (name) VALUES ('Огурцы');
INSERT INTO Ingredients (name) VALUES ('Грибы');

INSERT INTO m2m_Pizzas_Ingredients (pizza_id, ingredient_id) VALUES (1, 1);
INSERT INTO m2m_Pizzas_Ingredients (pizza_id, ingredient_id) VALUES (1, 2);
INSERT INTO m2m_Pizzas_Ingredients (pizza_id, ingredient_id) VALUES (1, 3);
INSERT INTO m2m_Pizzas_Ingredients (pizza_id, ingredient_id) VALUES (1, 4);
INSERT INTO m2m_Pizzas_Ingredients (pizza_id, ingredient_id) VALUES (2, 1);
INSERT INTO m2m_Pizzas_Ingredients (pizza_id, ingredient_id) VALUES (2, 3);
INSERT INTO m2m_Pizzas_Ingredients (pizza_id, ingredient_id) VALUES (2, 5);
INSERT INTO m2m_Pizzas_Ingredients (pizza_id, ingredient_id) VALUES (2, 6);

INSERT INTO Customers (firstname, lastname) VALUES ('Иван', 'Иванов');
INSERT INTO Customers (firstname, lastname) VALUES ('Василий', 'Васильев');

INSERT INTO Addresses (id, city, street, building) VALUES (1, 'Нижневартовск', 'Дружбы народов', '22');
INSERT INTO Addresses (id, city, street, building) VALUES (2, 'Москва', 'Мира', '12');

INSERT INTO Orders (customer_id) VALUES (1);
INSERT INTO Orders (customer_id) VALUES (1);
INSERT INTO Orders (customer_id) VALUES (2);
INSERT INTO Orders (customer_id) VALUES (2);

INSERT INTO m2m_Pizza_Order (pizza_id, order_id) VALUES (1, 1);
INSERT INTO m2m_Pizza_Order (pizza_id, order_id) VALUES (2, 1);
INSERT INTO m2m_Pizza_Order (pizza_id, order_id) VALUES (1, 2);
INSERT INTO m2m_Pizza_Order (pizza_id, order_id) VALUES (1, 3);
INSERT INTO m2m_Pizza_Order (pizza_id, order_id) VALUES (2, 3);
INSERT INTO m2m_Pizza_Order (pizza_id, order_id) VALUES (1, 3);
INSERT INTO m2m_Pizza_Order (pizza_id, order_id) VALUES (1, 4);