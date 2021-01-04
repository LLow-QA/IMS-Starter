DROP schema IF EXISTS store_db;
CREATE SCHEMA IF NOT EXISTS `store_db`;
USE `store_db` ;
CREATE TABLE IF NOT EXISTS `store_db`.`customers` (
    `customer_id` BIGINT NOT NULL AUTO_INCREMENT,
    `fName` VARCHAR(45) NULL DEFAULT NULL,
    `lName` VARCHAR(45) NULL DEFAULT NULL,
    `age` INT NOT NULL,
    `email` VARCHAR(100) UNIQUE NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `address` VARCHAR(100) NOT NULL,
    `postcode` VARCHAR(8) NOT NULL,
    PRIMARY KEY (`customer_id`)
);
CREATE TABLE IF NOT EXISTS `store_db`.`products` (
  `product_id` BIGINT NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) NOT NULL,
  `product_desc` varchar(200) DEFAULT NULL,
  `price` DOUBLE(6,2) NOT NULL,
  `stock` int NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_name_UNIQUE` (`product_name`)
);
CREATE TABLE IF NOT EXISTS `store_db`.`orders` (
  `order_id` BIGINT NOT NULL AUTO_INCREMENT,
  `customer_id` BIGINT NOT NULL,
  `date_ordered` date NOT NULL,
  `total_cost` DOUBLE (6,2) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id_idx` (`customer_id`),
  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
);
CREATE TABLE IF NOT EXISTS `store_db`.`orderline` (
  `orderline_id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT DEFAULT NULL,
  `product_id` BIGINT DEFAULT NULL,
  `product_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderline_id`),
  KEY `order_id_idx` (`order_id`),
  KEY `product_id_idx` (`product_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
);
