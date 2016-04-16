/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

CREATE TABLE IF NOT EXISTS `cards` (
  `c_id` INTEGER PRIMARY KEY,
  `c_value` varchar(25) DEFAULT NULL,
  `c_color` varchar(25) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `deck` (
  `d_t_id` int(5) NOT NULL,
  `d_m_id` int(5) NOT NULL,
  `d_c_id` int(5) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `games` (
  `g_id` INTEGER PRIMARY KEY,
  `g_nom` varchar(50) NOT NULL,
  `g_nbr_max_joueur` int(2) DEFAULT NULL,
  `g_nbr_max_ia` int(2) DEFAULT NULL,
  `g_etat` int(2) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `hands_players_in_game` (
  `h_id_match` int(5) NOT NULL,
  `h_id_user` int(5) NOT NULL,
  `h_id_card` int(5) NOT NULL,
  `h_tour` int(5) NOT NULL
);

CREATE TABLE IF NOT EXISTS `matchs` (
  `m_id` INTEGER PRIMARY KEY,
  `m_g_id` int(5) NOT NULL
);

CREATE TABLE IF NOT EXISTS `players_in_game` (
  `p_g_id` int(5) NOT NULL,
  `p_id_user` int(5) NOT NULL,
  `p_position` int(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS `stack` (
  `s_t_id` int(5) NOT NULL,
  `s_m_id` int(5) NOT NULL,
  `s_c_id` int(5) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `stats` (
  `st_g_id` int(5) NOT NULL,
  `st_u_id` int(5) NOT NULL,
  `nbr_of_cards_in_hand` int(3) NOT NULL,
  `nbr_of_strokes` int(5) NOT NULL,
  `st_score` int(7) NOT NULL
);

CREATE TABLE IF NOT EXISTS `statut` (
  `s_id` INTEGER PRIMARY KEY,
  `s_libelle` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `turns` (
  `t_id` INTEGER PRIMARY KEY,
  `t_m_id` int(7) NOT NULL,
  `t_sens` tinyint(1) NOT NULL,
  `id_user_ready` int(5) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `users` (
  `u_id` INTEGER PRIMARY KEY,
  `u_pseudo` varchar(30) NOT NULL,
  `u_email` varchar(50) DEFAULT NULL,
  `u_password` varchar(64) DEFAULT NULL,
  `u_statut` int(5) NOT NULL,
  `u_banned` tinyint(1) NOT NULL DEFAULT '0'
);

INSERT INTO statut (`s_id`, `s_libelle`) VALUES (1, 'bot');
INSERT INTO statut (`s_id`, `s_libelle`) VALUES (2, 'guest');
INSERT INTO statut (`s_id`, `s_libelle`) VALUES (3, 'member');
INSERT INTO statut (`s_id`, `s_libelle`) VALUES (4, 'admin');
INSERT INTO users (u_id, u_pseudo, u_email, u_password, u_statut, u_banned) VALUES (1, 'admin', 'admin@admin.fr', '4xB/NgBysNuegEkv5fQ7vg==', 4, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
