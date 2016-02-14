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

-- --------------------------------------------------------

--
-- Structure de la table `cards`
--

CREATE TABLE IF NOT EXISTS `cards` (
`c_id` int(5) NOT NULL,
  `c_value` varchar(20) NOT NULL,
  `c_color` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `games`
--

CREATE TABLE IF NOT EXISTS `games` (
`g_id` int(5) NOT NULL,
  `g_id_players_list` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `hands_players_in_game`
--

CREATE TABLE IF NOT EXISTS `hands_players_in_game` (
  `h_id_game` int(5) NOT NULL,
  `h_id_player` int(5) NOT NULL,
  `h_id_card` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `players_in_game`
--

CREATE TABLE IF NOT EXISTS `players_in_game` (
  `p_id` int(5) NOT NULL,
  `p_id_player` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
`u_id` int(5) NOT NULL,
  `u_pseudo` varchar(20) NOT NULL,
  `u_email` varchar(50) NOT NULL,
  `u_password` varchar(40) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`u_id`, `u_pseudo`, `u_email`, `u_password`) VALUES
(1, 'test', 'test@gmail.com', '12345');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `cards`
--
ALTER TABLE `cards`
 ADD PRIMARY KEY (`c_id`), ADD KEY `c_id` (`c_id`);

--
-- Index pour la table `games`
--
ALTER TABLE `games`
 ADD PRIMARY KEY (`g_id`);

--
-- Index pour la table `hands_players_in_game`
--
ALTER TABLE `hands_players_in_game`
 ADD KEY `h_id_card` (`h_id_card`), ADD KEY `h_id_player` (`h_id_player`), ADD KEY `h_id_game` (`h_id_game`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`u_id`,`u_pseudo`,`u_email`), ADD UNIQUE KEY `u_id` (`u_id`), ADD UNIQUE KEY `u_pseudo` (`u_pseudo`), ADD UNIQUE KEY `u_email` (`u_email`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `cards`
--
ALTER TABLE `cards`
MODIFY `c_id` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `games`
--
ALTER TABLE `games`
MODIFY `g_id` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
MODIFY `u_id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `hands_players_in_game`
--
ALTER TABLE `hands_players_in_game`
ADD CONSTRAINT `hands_players_in_game_ibfk_1` FOREIGN KEY (`h_id_card`) REFERENCES `cards` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `hands_players_in_game_ibfk_2` FOREIGN KEY (`h_id_game`) REFERENCES `games` (`g_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
