create table Pizzas
(
    id    int auto_increment
        primary key,
    name  varchar(30)    not null,
    price decimal(12, 2) not null,
    constraint Pizzas_UQ
        unique (name)
) collate = utf8mb4_general_ci;

create table Ingredients
(
    id   int auto_increment
        primary key,
    name varchar(50) not null,
    constraint Ingredients_Title_UQ
        unique (name)
) collate = utf8mb4_general_ci;

create table m2m_Pizzas_Ingredients
(
    id            int auto_increment
        primary key,
    ingredient_id int not null,
    pizza_id      int not null,
    constraint m2m_Pizza_Ingredient_Ingredients_FK
        foreign key (ingredient_id) references Ingredients (id)
            on delete cascade,
    constraint m2m_Pizza_Ingredient_Pizzas_FK
        foreign key (pizza_id) references Pizzas (id)
            on delete cascade
) collate = utf8mb4_general_ci;

create table Customers
(
    id        int auto_increment
        primary key,
    firstname varchar(50) not null,
    lastname  varchar(50) not null
) collate = utf8mb4_general_ci;

create table Addresses
(
    id       int         not null
        primary key,
    city     varchar(50) null,
    street   varchar(50) not null,
    building varchar(50) not null,
    constraint Addresses_Customers_id_fk
        foreign key (id) references Customers (id)
            on update cascade on delete cascade
) collate = utf8mb4_general_ci;

create table Orders
(
    id          int auto_increment
        primary key,
    customer_id int not null,
    constraint Orders_Customers_id_fk
        foreign key (customer_id) references Customers (id)
            on delete cascade
);

create table m2m_Pizza_Order
(
    id       int auto_increment
        primary key,
    pizza_id int not null,
    order_id int not null,
    constraint m2m_pizza_order_Orders_customer_id_fk
        foreign key (order_id) references Orders (id)
            on delete cascade,
    constraint m2m_pizza_order_Pizzas_id_fk
        foreign key (pizza_id) references Pizzas (id)
            on delete cascade
);



