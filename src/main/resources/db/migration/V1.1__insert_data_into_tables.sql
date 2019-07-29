insert into users (name, password) values
('Molly', '1234'),
('Ada', 'qwer'),
('Lily', 'yuiyui'),
('Rebecca', 'virginia')
;
commit;

insert into accountInfo (balance, accountType,users_id) values
(500.0, 'debit card',1),
(600.9, 'credit card',1),
(3000, 'alipay',1),
(1000, 'debit card',2),
(200, 'credit card',2),
(5000, 'alipay',2),
(1000, 'debit card',3),
(200, 'credit card',4),
(5000, 'alipay',4)
;
commit;

insert into record (type, amount,date,description,accountInfo_id) values
('food', 15.99, '2019-07-22 12:30:59' , 'chick-fill-A',2),
('clothes', 79.19,'2019-07-23 09:30:59','zara',2),
('food', 15.99,'2019-07-22 12:36:39','chick-fill-A',5),
('food', 15.99,'2019-07-22 12:37:39','chick-fill-A',7),
('food', 15.99,'2019-07-22 12:39:50','chick-fill-A',8)
;
commit;

