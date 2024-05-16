

##DATABASE CODE
```
CREATE SCHEMA `globantauth` ;

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

INSERT INTO `globantauth`.`role` (`id_role`, `role`) VALUES ('1', 'ADMIN');
INSERT INTO `globantauth`.`role` (`id_role`, `role`) VALUES ('2', 'USER');

CREATE TABLE `globantauth`.`user_role` (
`id_user_role` INT NOT NULL AUTO_INCREMENT,
`user_id` INT NOT NULL,
`role_id` INT NOT NULL,
PRIMARY KEY (`id_user_role`),
CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id_role`));
```
