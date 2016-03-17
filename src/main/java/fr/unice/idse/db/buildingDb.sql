-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Jeu 17 Mars 2016 à 16:21
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
  `c_value` varchar(25) DEFAULT NULL,
  `c_color` varchar(25) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `deck`
--

CREATE TABLE IF NOT EXISTS `deck` (
  `d_t_id` int(5) NOT NULL,
  `d_m_id` int(5) NOT NULL,
  `d_c_id` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `games`
--

CREATE TABLE IF NOT EXISTS `games` (
  `g_id` int(5) NOT NULL,
  `g_nom` varchar(50) NOT NULL,
  `g_nbr_max_joueur` int(2) DEFAULT NULL,
  `g_nbr_max_ia` int(2) DEFAULT NULL,
  `g_etat` int(2) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `hands_players_in_game`
--

CREATE TABLE IF NOT EXISTS `hands_players_in_game` (
  `h_id_match` int(5) NOT NULL,
  `h_id_user` int(5) NOT NULL,
  `h_id_card` int(5) NOT NULL,
  `h_tour` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `matchs`
--

CREATE TABLE IF NOT EXISTS `matchs` (
`m_id` int(7) NOT NULL,
  `m_g_id` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `players_in_game`
--

CREATE TABLE IF NOT EXISTS `players_in_game` (
  `p_g_id` int(5) NOT NULL,
  `p_id_user` int(5) NOT NULL,
  `p_position` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `stack`
--

CREATE TABLE IF NOT EXISTS `stack` (
  `s_t_id` int(5) NOT NULL,
  `s_m_id` int(5) NOT NULL,
  `s_c_id` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `stats`
--

CREATE TABLE IF NOT EXISTS `stats` (
  `st_g_id` int(5) NOT NULL,
  `st_u_id` int(5) NOT NULL,
  `nbr_of_cards_in_hand` int(3) NOT NULL,
  `nbr_of_strokes` int(5) NOT NULL,
  `st_score` int(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `statut`
--

CREATE TABLE IF NOT EXISTS `statut` (
  `s_id` int(5) NOT NULL,
  `s_libelle` varchar(50) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `statut`
--

INSERT INTO `statut` (`s_id`, `s_libelle`) VALUES
(4, 'admin'),
(1, 'bot'),
(2, 'guest'),
(3, 'member');

-- --------------------------------------------------------

--
-- Structure de la table `turns`
--

CREATE TABLE IF NOT EXISTS `turns` (
  `t_id` int(7) NOT NULL,
  `t_m_id` int(7) NOT NULL,
  `t_sens` tinyint(1) NOT NULL,
  `id_user_ready` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `u_id` int(5) NOT NULL,
  `u_pseudo` varchar(30) NOT NULL,
  `u_email` varchar(50) DEFAULT NULL,
  `u_password` varchar(64) DEFAULT NULL,
  `u_statut` int(5) NOT NULL,
  `u_banned` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `cards`
--
ALTER TABLE `cards`
 ADD PRIMARY KEY (`c_id`);

--
-- Index pour la table `deck`
--
ALTER TABLE `deck`
 ADD PRIMARY KEY (`d_t_id`,`d_m_id`), ADD KEY `FK_deck_match` (`d_m_id`);

--
-- Index pour la table `games`
--
ALTER TABLE `games`
 ADD PRIMARY KEY (`g_id`), ADD UNIQUE KEY `g_nom` (`g_nom`);

--
-- Index pour la table `hands_players_in_game`
--
ALTER TABLE `hands_players_in_game`
 ADD PRIMARY KEY (`h_id_match`,`h_id_user`,`h_id_card`), ADD KEY `fk_user_hand` (`h_id_user`), ADD KEY `fk_card_hand` (`h_id_card`);

--
-- Index pour la table `matchs`
--
ALTER TABLE `matchs`
 ADD PRIMARY KEY (`m_id`), ADD KEY `fk_matchs_game` (`m_g_id`);

--
-- Index pour la table `players_in_game`
--
ALTER TABLE `players_in_game`
 ADD PRIMARY KEY (`p_g_id`,`p_id_user`), ADD KEY `fk_user_in_game` (`p_id_user`);

--
-- Index pour la table `stack`
--
ALTER TABLE `stack`
 ADD PRIMARY KEY (`s_t_id`,`s_m_id`), ADD KEY `FK_stack_match` (`s_m_id`);

--
-- Index pour la table `stats`
--
ALTER TABLE `stats`
 ADD PRIMARY KEY (`st_g_id`,`st_u_id`), ADD KEY `fk_score_user` (`st_u_id`);

--
-- Index pour la table `statut`
--
ALTER TABLE `statut`
 ADD PRIMARY KEY (`s_id`), ADD UNIQUE KEY `s_libelle` (`s_libelle`);

--
-- Index pour la table `turns`
--
ALTER TABLE `turns`
 ADD PRIMARY KEY (`t_id`), ADD KEY `fk_match_turns` (`t_m_id`), ADD KEY `fk_user_turns` (`id_user_ready`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`u_id`), ADD UNIQUE KEY `u_pseudo` (`u_pseudo`,`u_email`), ADD KEY `fk_user_statut` (`u_statut`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `cards`
--
ALTER TABLE `cards`
MODIFY `c_id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT pour la table `games`
--
ALTER TABLE `games`
MODIFY `g_id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT pour la table `matchs`
--
ALTER TABLE `matchs`
MODIFY `m_id` int(7) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `statut`
--
ALTER TABLE `statut`
MODIFY `s_id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `turns`
--
ALTER TABLE `turns`
MODIFY `t_id` int(7) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
MODIFY `u_id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `deck`
--
ALTER TABLE `deck`
ADD CONSTRAINT `FK_deck_match` FOREIGN KEY (`d_m_id`) REFERENCES `matchs` (`m_id`),
ADD CONSTRAINT `FK_deck_turn` FOREIGN KEY (`d_t_id`) REFERENCES `turns` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `hands_players_in_game`
--
ALTER TABLE `hands_players_in_game`
ADD CONSTRAINT `fk_card_hand` FOREIGN KEY (`h_id_card`) REFERENCES `cards` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_match_hand` FOREIGN KEY (`h_id_match`) REFERENCES `matchs` (`m_id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_user_hand` FOREIGN KEY (`h_id_user`) REFERENCES `users` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `matchs`
--
ALTER TABLE `matchs`
ADD CONSTRAINT `fk_matchs_game` FOREIGN KEY (`m_g_id`) REFERENCES `games` (`g_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `players_in_game`
--
ALTER TABLE `players_in_game`
ADD CONSTRAINT `fk_game_player` FOREIGN KEY (`p_g_id`) REFERENCES `games` (`g_id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_user_in_game` FOREIGN KEY (`p_id_user`) REFERENCES `users` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `stack`
--
ALTER TABLE `stack`
ADD CONSTRAINT `FK_stack_match` FOREIGN KEY (`s_m_id`) REFERENCES `matchs` (`m_id`),
ADD CONSTRAINT `FK_stack_turn` FOREIGN KEY (`s_t_id`) REFERENCES `turns` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `stats`
--
ALTER TABLE `stats`
ADD CONSTRAINT `fk_score_game` FOREIGN KEY (`st_g_id`) REFERENCES `games` (`g_id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_score_user` FOREIGN KEY (`st_u_id`) REFERENCES `users` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `turns`
--
ALTER TABLE `turns`
ADD CONSTRAINT `fk_match_turns` FOREIGN KEY (`t_m_id`) REFERENCES `matchs` (`m_id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `fk_user_turns` FOREIGN KEY (`id_user_ready`) REFERENCES `users` (`u_id`);

--
-- Contraintes pour la table `users`
--
ALTER TABLE `users`
ADD CONSTRAINT `fk_user_statut` FOREIGN KEY (`u_statut`) REFERENCES `statut` (`s_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
