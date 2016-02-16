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




/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
