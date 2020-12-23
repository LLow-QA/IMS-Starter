INSERT INTO `store_db`.`customers` (`customer_id`,`fName`, `lName`, `age`, `email`, `password`,`address`,`postcode`) VALUES (1,'jordan', 'harrison',22,'lafga@ufgs.net','dgsfhaf8g','343 fasd sf','DC3 4rd');
INSERT INTO `store_db`.`products`  (`product_id`,`product_name`,`product_desc`,`price`,`stock`) VALUES (1,`Apple`,`A very tasty apple`,1.99,100);
INSERT INTO `store_db`.`orders`  (`order_id`,`customer_id`,`date_ordered`,`total_cost`) VALUES (1,1,2020/12/23,3.98);
INSERT INTO `store_db`.`orderline`  (`orderline_id`,`order_id`,`product_id`,`product_quantity`) VALUES (1,1,1,2);