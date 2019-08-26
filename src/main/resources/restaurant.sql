
LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT IGNORE INTO `bill` (`id`) VALUES (1);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT IGNORE INTO `menu` (`id`,`description`,`details`,`image_url`,`name`,`price`) VALUES (1,'All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style.','Italian,Ham,Pineapple','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg','Hawaiian Pizza',300.00),(2,'Best marinated chicken with pineapple and mushroom on Spicy Lemon sauce. Enjoy our tasty Thai style pizza.','Italian,Thai,Chicken,Mushroom,Hot','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu2.jpg','Chicken Tom Yum Pizza',350.00),(3,'Chinese steamed bun','Chinese,Pork,Recommended','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu3.jpg','Xiaolongbao',200.00),(4,'Traditional side dish made from salted and fermented vegetables','Korean,Radish,Cabbage','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu4.jpg','Kimchi',50.00),(5,'Partially fermented tea grown in the Alishan area','Hot,Non-alcohol','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu5.jpg','Oolong tea',30.00),(6,'Fantastic flavors and authentic regional appeal beer','Alcohol','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu6.jpg','Beer',60.00);
UNLOCK TABLES;

LOCK TABLES `bill_item` WRITE;
/*!40000 ALTER TABLE `bill_item` DISABLE KEYS */;
INSERT IGNORE INTO `bill_item` (`id`,`ordered_time`,`quantity`,`bill_id`,`menu_id`)  VALUES (1,now(),2,1,1);
/*!40000 ALTER TABLE `bill_item` ENABLE KEYS */;
UNLOCK TABLES;




