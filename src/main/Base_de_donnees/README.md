# Base de donnée du Uno

### Composition de la base de donée 

La base de donnée est actuellement composée de 11 tables :

- Users
- Statut_users
- Games
- Matchs
- Players_in_games
- Hand_players_in _game
- Cards
- Deck
- Stack
- Statistique

### Table Users

>###### Composition de la table 

Cette table est composée de 5 champs : 

- u_id
- u_email
- u_pseudo
- u_passeword
- u_id_statut

>###### Explication des choix d'utilisations

- u_id correspond à la clé primaire de la table 

- Le u_passeword est de format varchar(64) afin de permettre un encodage du mot de passe en sha256

- Le u_id_statut permet à a table Users  l'enregistrement de tous les types d'utilisateurs nécéssaire : 

		- humain
		- IA
		- Admin 

Ces types utilisateur sont gérés dans la table statut_users. La table users est composé d'une clé étrangère Id_statut_users afin de pouvoir acceder aux données de la table Statut_users. 

### Table Games 

>###### Composition de la table 

Cette table est composée de 5 champs :

- g_id
- g_name
- g_nbr_max_joueur
- g_nbr_max_IA
- g_etat

>###### Explication des choix d'utilisations 

- g_id correspond à la clé primaire de la tables

-g_nbr_max permet de définir le nombre maximum de joueur dans une partie. Ainsi, si le nombre de joueur maximum est définit à 4, alors uniquement quatre joueurs pourront être présent dans la table player_in _game pour un id_g donné. 

- g_nbr_max correspond à la même chose que le g_nbr_max_joueur. Le nombre maxmum d'IA rentrée dans ce champ pour une partie donnée pourront être présent dans la table player_in_games. 

- g_etat permertra de définir l'état d'une partie à savoir si elle est encours, en attente de joueur, finit, etc ... 

