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
-- Structure de la table 'games'
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


-- Structure de la table 'statut_users'
-- Création de la table 
CREATE TABLE IF NOT EXISTS `statut_users`(
`su_id` INT (5) AUTO_INCREMENT PRIMARY KEY NOT NULL,
`su_wording` VARCHAR (25) NOT NULL
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

-- Structure de la table 'users'
-- Création de la table 

CREATE TABLE IF NOT EXISTS `users`(
`u_id` INT (5) AUTO_INCREMENT PRIMARY KEY NOT NULL,
`u_pseudo` VARCHAR (30) NOT NULL,
`u_email` VARCHAR (50) NOT NULL,
`u_password` VARCHAR (64) NOT NULL,
`u_su_id` INT (5),
-- Ajout champ unique 
UNIQUE (u_pseudo, u_email),
-- Ajout des contraintes des clés étrangère 
 CONSTRAINT fk_statut_users          
        FOREIGN KEY (u_su_id)            
        REFERENCES statut_users(su_id) ON DELETE CASCADE ON UPDATE CASCADE

)ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

-- Structure de la table 'cards'
-- Création de la table 

CREATE TABLE IF NOT EXISTS `cards`(
`c_id` INT (5) AUTO_INCREMENT PRIMARY KEY NOT NULL,
`c_value` ENUM ('zero', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine', 'skip', 'reverse', 'drawtwo', 'drawfour', 'wild'), 
`c_color` ENUM ('blue', 'green', 'red', 'yellow', 'black')
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;


-- Structure de la table 'matchs'
-- Création de la table 

CREATE TABLE IF NOT EXISTS `matchs`(
`m_id` INT (7) AUTO_INCREMENT PRIMARY KEY  NOT NULL,
`m_g_id`INT (5) NOT NULL,
-- Ajout des contraintes des clés étrangère 
 CONSTRAINT fk_matchs_game          
        FOREIGN KEY (m_g_id)            
        REFERENCES games(g_id) ON DELETE CASCADE ON UPDATE CASCADE

)ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;


-- Structure de la table 'turns'
-- Création de la table 

CREATE TABLE IF NOT EXISTS `turns`(
`t_id` INT (7) AUTO_INCREMENT PRIMARY KEY  NOT NULL,
`t_m_id`INT (7) NOT NULL,
`t_sens` , 
`id_user_ready` INT (5),
-- Ajout des contraintes des clés étrangère 
 CONSTRAINT fk_match_turns          
        FOREIGN KEY (t_m_id)            
        REFERENCES matchs(m_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_turns
  		FOREIGN KEY (id_user_ready)
  		REFERENCES users(u_id)

)ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;


-- Structure de la table 'hands_players_in_game'
-- Création de la table

CREATE TABLE IF NOT EXISTS `hands_players_in_game` (
  `h_id_match` int(5) NOT NULL,
  `h_id_user` int(5) NOT NULL,
  `h_id_card` int(5) NOT NULL,
  `h_tour` int (5) NOT NULL,
  -- Ajout de la clé primaire composite 
  PRIMARY KEY (h_id_match,h_id_user,h_id_card),
  -- Ajout des contraintes des clés étrangère 
  CONSTRAINT fk_match_hand          
        FOREIGN KEY (h_id_match)            
        REFERENCES matchs(m_id) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT fk_user_hand          
        FOREIGN KEY (h_id_user)            
        REFERENCES users(u_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_card_hand          
        FOREIGN KEY (h_id_card)            
        REFERENCES cards(c_id) ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Structure de la table 'players_in_game'
-- Création de la table

CREATE TABLE IF NOT EXISTS `players_in_game` (
  `p_g_id` int(5) NOT NULL,
  `p_id_user` int(5) NOT NULL,
  `p_position` int (1) Not NULL,
  -- Ajout de la clé primaire composite 
  PRIMARY KEY (p_g_id,p_id_user),
   -- Ajout des contraintes des clés étrangère 
  CONSTRAINT fk_game_player          
        FOREIGN KEY (p_g_id)            
        REFERENCES games(g_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_in_game          
        FOREIGN KEY (p_id_user)            
        REFERENCES users(u_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- Structure de la table 'DECK'
-- Création de la table 
CREATE TABLE IF NOT EXISTS `deck`(
`d_t_id` int(5) NOT NULL,
`d_m_id` int(5) NOT NULL,
`d_c_id` INT (5)
  )



-- Structure de la table 'stats'
-- Création de la table

CREATE TABLE IF NOT EXISTS `stats`(
`s_g_id` INT (5) NOT NULL,
`s_u_id` INT (5) NOT NULL,
`nb_of_cards_in_hand` INT (3) NOT NULL,
`nb_of_strokes` INT (5) NOT NULL,
`score` INT (7) NOT NULL,
-- Ajout de la clé primaire composite 
PRIMARY KEY (s_g_id,s_u_id),
 -- Ajout des contraintes des clés étrangère 
  CONSTRAINT fk_score_game          
        FOREIGN KEY (s_g_id)            
        REFERENCES games(g_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_score_user          
        FOREIGN KEY (s_u_id)            
        REFERENCES users(u_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
