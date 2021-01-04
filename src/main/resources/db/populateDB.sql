DELETE FROM user_role;
DELETE FROM vote;
DELETE FROM users;
DELETE FROM restaurant;
DELETE FROM dish;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurant (name)
VALUES ('Corner Grill'),
       ('Bluefin'),
       ('McDonalds'),
       ('Caesars Palace');

INSERT INTO vote (user_id, restaurant_id, localdate)
VALUES (100000, 100002, '2020-08-10'),
       (100000, 100002, '2020-08-11'),
       (100000, 100004, '2020-08-12'),
       (100001, 100003, '2020-08-10'),
       (100001, 100003, '2020-08-11'),
       (100001, 100005, '2020-08-12'),
       (100001, 100002, now());

INSERT INTO dish (restaurant_id, name, price, localdate)
VALUES (100002, 'Caprese Burger', 500, '2020-08-10'),
 (100002, 'Borsch', 300, '2020-08-10'),
 (100002, 'New York Cheesecake', 350, '2020-08-10'),
 (100002, 'Bolognese Pasta', 250, '2020-08-11'),
 (100002, 'Chicken Noodles', 150, '2020-08-11'),
 (100002, 'Chiefs special', 500, '2020-08-11'),
 (100002, 'Corsican Burger', 500, '2020-08-12'),
 (100002, 'Tom Yum', 400, '2020-08-12'),
 (100002, 'Belgian Waffles', 375, '2020-08-12'),
 (100002, 'Combo box', 999, now()),
 (100003, 'California Roll', 1000, '2020-08-10'),
 (100003, 'Philly roll', 1200, '2020-08-10'),
 (100003, 'Salmon roll', 700, '2020-08-10'),
 (100003, 'California Roll', 1000, '2020-08-11'),
 (100003, 'Philly roll', 1200, '2020-08-11'),
 (100003, 'Salmon roll', 700, '2020-08-11'),
 (100003, 'California Roll', 1000, '2020-08-12'),
 (100003, 'Philly roll', 1200, '2020-08-12'),
 (100003, 'Salmon roll', 700, '2020-08-12'),
 (100003, 'California Roll', 1000, now()),
 (100003, 'Philly roll', 1200, now()),
 (100003, 'Salmon roll', 700, now()),
 (100004, 'MacCombo', 300, '2020-08-10'),
 (100004, 'MacCombo', 300, '2020-08-11'),
 (100004, 'MacCombo', 300, '2020-08-12'),
 (100004, 'MacCombo', 300, now()),
 (100004, 'Kobe Steak', 10000, '2020-08-10'),
 (100004, 'Fugu Fish', 50000, '2020-08-11'),
 (100004, 'Gold on a plate', 500000, '2020-08-12'),
 (100004, 'Something expensive', 100000, now());