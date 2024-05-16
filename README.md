

CREATE TABLE `globantauth`.`user` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL,
`surname` VARCHAR(45) NOT NULL,
`password` VARCHAR(45) NOT NULL,
`email` VARCHAR(45) NOT NULL,
`role` INT NOT NULL,
PRIMARY KEY (`id`));

CREATE TABLE `globantauth`.`role` (
`id_role` INT NOT NULL AUTO_INCREMENT,
`role` VARCHAR(45) NOT NULL,
PRIMARY KEY (`id_role`));
