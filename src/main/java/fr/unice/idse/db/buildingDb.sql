-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Dim 14 Février 2016 à 16:42
-- Version du serveur :  5.6.20-log
-- Version de PHP :  5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `uno`
--
CREATE DATABASE IF NOT EXISTS `uno` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `uno`;

--
-- Structure de la table 'GAMES'
-- Création de la table 
--
CREATE TABLE IF NOT EXISTS `games`(
`g_id` INT(5) AUTO_INCREMENT PRIMARY KEY NOT NULL,
`g_nom` VARCHAR (50) NOT NULL,
`g_nbr_max_joueur` INT (2),
`g_nbr_max_ia` INT (2),
`g_etat` INT (2),
UNIQUE (g_nom)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

-- Structure de la table 'USERS'
-- Création de la table 

CREATE TABLE IF NOT EXISTS `users`(
`u_id` INT (5) AUTO_INCREMENT PRIMARY KEY NOT NULL,
`u_pseudo` VARCHAR (30) NOT NULL,
`u_email` VARCHAR (50),
`u_password` VARCHAR (64),
`u_statut` INT (2),
UNIQUE (u_pseudo,u_email)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

-- Structure de la table 'CARDS'
-- Création de la table 

CREATE TABLE IF NOT EXISTS `cards`(
`c_id` INT (5) AUTO_INCREMENT PRIMARY KEY NOT NULL,
`c_value` ENUM ('zero','one','two','three','four','five','six','seven','eight','nine','skip', 'reverse','drawtwo','drawfour','wild'), 
`c_color` ENUM ('blue','green', 'red', 'yellow', 'black')
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;


-- Structure de la table 'hands_players_in_game'
-- Création de la table
CREATE TABLE IF NOT EXISTS `hands_players_in_game` (
  `h_id_game` int(5) NOT NULL,
  `h_id_user` int(5) NOT NULL,
  `h_id_card` int(5) NOT NULL,
  -- Ajout de la clé primaire composite 
  PRIMARY KEY (h_id_game,h_id_user,h_id_card),
  -- Ajout des contraintes des clés étrangère 
  CONSTRAINT fk_game_hand          
        FOREIGN KEY (h_id_game)            
        REFERENCES games(g_id) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT fk_user_hand          
        FOREIGN KEY (h_id_user)            
        REFERENCES users(u_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_card_hand          
        FOREIGN KEY (h_id_card)            
        REFERENCES cards(c_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
