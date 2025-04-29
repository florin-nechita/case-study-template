CREATE SCHEMA IF NOT EXISTS rohlik;

CREATE TYPE order_status AS ENUM ('PENDING', 'CANCELED', 'EXPIRED', 'PAID');

CREATE TABLE rohlik.products
(
    id                SERIAL         NOT NULL PRIMARY KEY,
    name              VARCHAR(255)   NOT NULL,
    stock_amount INTEGER        NOT NULL,
    price             NUMERIC(19, 2) NOT NULL,
    created_at        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rohlik.orders
(
    id             SERIAL             NOT NULL PRIMARY KEY,
    order_status   order_status NOT NULL,
    paid_at        TIMESTAMP,
    created_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rohlik.order_items
(
    id         SERIAL  NOT NULL PRIMARY KEY,
    order_id   BIGINT  NOT NULL,
    product_id BIGINT  NOT NULL,
    quantity   INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES rohlik.orders (id),
    FOREIGN KEY (product_id) REFERENCES rohlik.products (id)
);

