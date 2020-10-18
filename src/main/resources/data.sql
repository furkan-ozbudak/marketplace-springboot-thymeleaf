
create table if not exists persistent_logins (
  username varchar_ignorecase(100) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
  );

INSERT INTO `ROLE` (name) VALUES ('ADMIN');
INSERT INTO `ROLE` (name) VALUES ('SELLER');
INSERT INTO `ROLE` (name) VALUES ('BUYER');

INSERT INTO `ADDRESS` (id, city, state, status, street, zip_code) VALUES (1, 'Fairfield', 'IA', 'Shipping', '1000 North 4th', '52557');
INSERT INTO `ADDRESS` (id, city, state, status, street, zip_code) VALUES (2, 'Fairfield-Shipping', 'IA', 'Shipping', '9874 North 10th', '52557');
INSERT INTO `ADDRESS` (id, city, state, status, street, zip_code) VALUES (3, 'Fairfield', 'IA', 'Shipping', '1000 North 4th', '52557');

INSERT INTO `CREDIT_CARD` (id, number, name, valid_date) VALUES (1, '1111444433339999', 'First User', '10/21');
INSERT INTO `CREDIT_CARD` (id, number, name, valid_date) VALUES (2, '2222555588889988', 'Second User', '09/21');
INSERT INTO `CREDIT_CARD` (id, number, name, valid_date) VALUES (3, '8888777766669977', 'Third User', '08/21');

INSERT INTO `CART` (id, total_price, active) VALUES (1, 0, true);
INSERT INTO `CART` (id, total_price, active) VALUES (2, 0, true);
INSERT INTO `CART` (id, total_price, active) VALUES (3, 0, true);


INSERT INTO `USER` (id, active, name, password, email, billing_address_id, shipping_address_id, type, credit_card_id, points, has_ads, cart_id) VALUES (1, true, 'First User', '$2a$10$6wt6Q1Mn/ab0BgK2gUhQ8.T5MP.6dsNt1Ugn0bCsm9QOtfoKXj9eu', 'first.user@gmail.com', 1, 2, 2, 1, 0, false, 2);
INSERT INTO `USER` (id, active, name, password, email, billing_address_id, shipping_address_id, type, credit_card_id, points, has_ads, cart_id) VALUES (5, true, 'Sanjay', '$2a$10$6wt6Q1Mn/ab0BgK2gUhQ8.T5MP.6dsNt1Ugn0bCsm9QOtfoKXj9eu', 'sanjtrital@gmail.com', 1, 2, 2, 1, 0, false, 2);
INSERT INTO `USER` (id, active, name, password, email, billing_address_id, shipping_address_id, type, credit_card_id, points, has_ads, cart_id) VALUES (2, true, 'Second User', '$2a$10$6wt6Q1Mn/ab0BgK2gUhQ8.T5MP.6dsNt1Ugn0bCsm9QOtfoKXj9eu', 'second.user@gmail.com', 1, 2, 3, 2, 0, false, 1);
INSERT INTO `USER` (id, active, name, password, email, billing_address_id, shipping_address_id, type, credit_card_id, points, has_ads, cart_id) VALUES (3, true, 'Third User', '$2a$10$6wt6Q1Mn/ab0BgK2gUhQ8.T5MP.6dsNt1Ugn0bCsm9QOtfoKXj9eu', 'third.user@gmail.com', 1, 2, 1, 3, 0, true, 3);
INSERT INTO `USER` (id, active, name, password, email, billing_address_id, shipping_address_id, type, credit_card_id, points, has_ads, cart_id) VALUES (4, true, 'John Smith', '$2a$10$6wt6Q1Mn/ab0BgK2gUhQ8.T5MP.6dsNt1Ugn0bCsm9QOtfoKXj9eu', 'ebusinessguide88@gmail.com', 1, 2, 3, 3, 0, true, 3);
INSERT INTO `USER` (id, active, name, password, email, billing_address_id, shipping_address_id, type, credit_card_id, points, has_ads, cart_id) VALUES (6, true, 'Michel', '$2a$10$6wt6Q1Mn/ab0BgK2gUhQ8.T5MP.6dsNt1Ugn0bCsm9QOtfoKXj9eu', 'm.ch@gmail.com', 1, 2, 2, 1, 0, false , 2);


INSERT INTO `PRODUCT` (id, name, description, price, seller_id, quantiy, product_image, enable) VALUES (1, 'Madhur', 'Product01 description', 10, 1, 2,'madhur.jpg', true);
INSERT INTO `PRODUCT` (id, name, description, price, seller_id, quantiy, product_image, enable) VALUES (2, 'Dream Catchers', 'Product02 description', 10, 1, 3,'dreamcatchers.jpg', true);
INSERT INTO `PRODUCT` (id, name, description, price, seller_id, quantiy, product_image, enable) VALUES (3, 'Product03', 'Product03 description', 10, 1, 2,'sneakers.jpg', true);
INSERT INTO `PRODUCT` (id, name, description, price, seller_id, quantiy, product_image, enable) VALUES (4, 'Green Tea', 'Product04 description', 10, 1, 3, 'greentea.jpg', true);
INSERT INTO `PRODUCT` (id, name, description, price, seller_id, quantiy, product_image, enable) VALUES (5, 'Kissan', 'Product05 description', 10, 3, 3,'kissan.jpg', true);
INSERT INTO `PRODUCT` (id, name, description, price, seller_id, quantiy, product_image, enable) VALUES (6, 'Odonil', 'Product06 description', 10, 3, 3,'odonil.jpg', true);
INSERT INTO `PRODUCT` (id, name, description, price, seller_id, quantiy, product_image, enable) VALUES (7, 'Surf Excel', 'Product07 description', 10, 3, 3,'surfexcel.jpg', true);
INSERT INTO `PRODUCT` (id, name, description, price, seller_id, quantiy, product_image, enable) VALUES (8, 'Scherwan Noodle', 'Product08 description', 10, 3, 3,'noodle.jpg', true);
INSERT INTO `PRODUCT` (id, name, description, price, seller_id, quantiy, product_image, enable) VALUES (9, 'Almonds', 'Almonds is very good for health', 10, 3, 3,'almonds.jpg', true);

INSERT INTO `CART_PRODUCT_LIST` (cart_id, product_list_id) VALUES (1, 1);
INSERT INTO `CART_PRODUCT_LIST` (cart_id, product_list_id) VALUES (1, 2);
INSERT INTO `CART_PRODUCT_LIST` (cart_id, product_list_id) VALUES (1, 3);
INSERT INTO `CART_PRODUCT_LIST` (cart_id, product_list_id) VALUES (1, 7);

INSERT INTO `ADS` (id, user_id) VALUES (1, 3);
INSERT INTO `ADS` (id, user_id) VALUES (2, 1);


INSERT INTO `REVIEW` (id, create_date,description,status,product_id,user_id) VALUES (1,'2019-2-2','Very Good',null,1,1);



INSERT INTO `REVIEW`(id, description, status, create_date,  product_id, user_id) VALUES (5,'Product is amazing', '','2019-05-01',1,4 );
INSERT INTO `REVIEW`(id, description, status, create_date,  product_id, user_id) VALUES (3,'I loved it', 'approved','2019-05-02',1,1 );
INSERT INTO `REVIEW`(id, description, status, create_date,  product_id, user_id) VALUES (7,'Not Good', 'approved','2019-05-11',1,1 );
INSERT INTO `REVIEW`(id, description, status, create_date,  product_id, user_id) VALUES (4,'Good I m happy', 'approved','2019-06-11',1,1 );


INSERT INTO `REVIEW`(id, description, status, create_date,  product_id, user_id) VALUES (9,'Not so Good Please', 'approved','2019-07-11',1,1 );



