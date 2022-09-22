create table allorder (
    id int primary key ,
    contract_num varchar(100),
    enterprise varchar(100),
    product_model varchar(100),
    quantity int,
    contract_manager int,
    contract_date varchar(100),
    estimated_delivery_date varchar(100),
    lodgement_date varchar(100),
    salesman_num int,
    contract_type varchar(100)
);

create table center (
    id int primary key ,
    name varchar(100)
);

create table contract (
    id int primary key ,
    contract_num varchar(100),
    enterprise varchar(100),
    manager varchar(100),
    supply_center varchar(100)
);

create table enterprise (
    id int primary key ,
    name varchar(100),
    country varchar(100),
    city varchar(100),
    supply_center varchar(100),
    industry varchar(100)
);

create table model (
    id int primary key ,
    number varchar(100),
    model varchar(100),
    name varchar(100),
    unit_price int
);

create table product (
    id int primary key ,
    supply_center varchar(100),
    product_model varchar(100),
    supply_staff int,
    date varchar(100),
    purchase_price int,
    quantity int,
    remain int
);

create table staff (
    id int primary key ,
    name varchar(100),
    age int,
    gender varchar(100),
    number int,
    supply_center varchar(100),
    mobile_number varchar(100),
    type varchar(100)
);