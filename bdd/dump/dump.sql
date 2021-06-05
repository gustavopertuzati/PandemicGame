CREATE SCHEMA IF NOT EXISTS `covid` DEFAULT CHARACTER SET utf8;

/*CREATE DATABASE `covid` CHARACTER SET 'utf8';*/

/*ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';*/

USE `covid`;

CREATE TABLE IF NOT EXISTS `covid`.`Country` (
  `slug` VARCHAR(127) NOT NULL,
  `name` VARCHAR(127) NOT NULL,
  `size` INT UNSIGNED NOT NULL,
  `latitude` INT UNSIGNED NOT NULL,
  `longitude` INT UNSIGNED NOT NULL,
  `total_population` INT UNSIGNED NOT NULL,
  `initial_total_cases` INT UNSIGNED NOT NULL,
  `initial_total_active` INT UNSIGNED NOT NULL,
  `initial_total_deaths` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `covid`.`Type` (
    `name` VARCHAR(127) NOT NULL,
    PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `covid`.`Perk` (
  `id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(127) NOT NULL,
  `description` VARCHAR(1023) NOT NULL,
  `price` INT UNSIGNED NOT NULL,
  `type` VARCHAR(127) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `perk_constraint`
    FOREIGN KEY (`type`) REFERENCES `covid`.`Type` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* valeurs par défaut à changer */
CREATE TABLE IF NOT EXISTS `covid`.`Virus` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `infectivity` FLOAT NOT NULL DEFAULT 0.15,
    `lethality` FLOAT NOT NULL DEFAULT 0.05,
    `resistance` FLOAT NOT NULL DEFAULT 0.005,
    `player_name` VARCHAR(127) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `covid`.`Game` (
  `virus_id` INT UNSIGNED NOT NULL,
  `start_date` VARCHAR(1023) NOT NULL,
  `current_date` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`virus_id`),
  CONSTRAINT `virus_constraint`
    FOREIGN KEY (`virus_id`) REFERENCES `covid`.`Virus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* cette table n'est pas générée */
CREATE TABLE IF NOT EXISTS `covid`.`State` (
  `game_id` INT UNSIGNED NOT NULL,
  `slug` VARCHAR(127) NOT NULL,
  `current_total_cases` INT UNSIGNED NOT NULL,
  `current_total_active` INT UNSIGNED NOT NULL,
  `current_total_deaths` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`game_id`, `slug`),
  CONSTRAINT `countryState_game_id_constraint`
    FOREIGN KEY (`game_id`) REFERENCES `covid`.`Game` (`virus_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `countryState_slug_constraint`
    FOREIGN KEY (`slug`) REFERENCES `covid`.`Country` (`slug`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `covid`.`UnlockedPerk` (
  `perk_id` INT UNSIGNED NOT NULL,
  `virus` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`perk_id`, `virus`),
  CONSTRAINT `unlocked_constraint_perk`
    FOREIGN KEY (`perk_id`)
    REFERENCES `covid`.`Perk` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `unlocked_constraint_virus`
    FOREIGN KEY (`virus`)
    REFERENCES `covid`.`Virus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;