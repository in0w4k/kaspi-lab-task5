create table t_products (
    id bigserial primary key,
    name varchar(255) not null,
    price numeric(10, 2) not null
);