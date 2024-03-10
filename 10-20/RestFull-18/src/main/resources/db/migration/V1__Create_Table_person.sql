CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(10) NOT NULL,
  `last_name` varchar(15) NOT NULL,
  `address` varchar(30) NOT NULL,
  `gender` varchar(6) NOT NULL,
  PRIMARY KEY (`id`)
);
