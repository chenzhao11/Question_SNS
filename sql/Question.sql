CREATE TABLE `practice`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `salt` VARCHAR(128) NULL,
  `head_url` VARCHAR(128) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));
CREATE TABLE `practice`.`question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(128) NOT NULL,
  `content` LONGTEXT NULL,
  `user_id` INT NOT NULL,
  `created_date` DATETIME NOT NULL,
  `comment_count` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `_idx` (`user_id` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `practice`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE TABLE `practice`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `status` INT NOT NULL,
  `expired` DATETIME NOT NULL,
  `ticket` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `ticket` (`ticket` ASC));
CREATE TABLE `practice`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` LONGTEXT NULL,
  `created_date` DATETIME NULL,
  `user_id` INT NOT NULL,
  `entity_id` INT NOT NULL,
  `entity_type` INT NOT NULL,
  `status` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `commentindex` (`entity_type` ASC, `entity_id` ASC));
CREATE TABLE `practice`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` LONGTEXT NOT NULL,
  `fromid` INT NOT NULL,
  `toid` INT NOT NULL,
  `conversation_id` VARCHAR(128) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `has_read` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `messageIndex` (`conversation_id` ASC, `toid` ASC, `fromid` ASC));
CREATE TABLE `practice`.`feed` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` INT NOT NULL,
  `data` LONGTEXT NULL,
  `created_date` DATETIME NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC));


