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

### Table Games

>###### Composition de la table 

Cette table est composée de 5 champs : 
- g_id qui correspond à la clé primaire de la table 
- g_nom 
- g_nbr_max_joueur
- g_nbr_max_ia
- g_etat

>###### Explication des choix d'utilisations

- Le champs g_nom est unique afin de permettre l'identification de la partie par son nom. 

- Le champs g_nombre_max_joueur permets de définir le nombre maximum de joueur dans une partie. Ainsi dans la table player_in_game il ne pourra pas avoir plus de joueur associé à une partie que le nombre maximum de joueur saisi dans cette partie la. Le champs g_nbr_max_ia fonctionne de la même manière mais pour les IA. 

- Le champ g_etat permet d'enregister l'état de la partie à savoir si elle est en attente de joueur, en cour, terminé, ... 


### Table Users

>###### Composition de la table 

Cette table est composée de 5 champs : 
- u_id qui correspond à la clé primaire de la table 
- u_email
- u_pseudo
- u_passeword
- u_id_statut

>###### Explication des choix d'utilisations

- Le u_passeword est de format varchar(64) afin de permettre un encodage du mot de passe en sha256

- Le u_id_statut permet à a table Users  l'enregistrement de tous les types d'utilisateurs nécéssaire : 
		- humain
		- IA
		- Admin 
Ces types utilisateur sont gérés dans la table statut_users. La table users est composé d'une clé étrangère Id_statut_users afin de pouvoir acceder aux données de la table Statut_users. 

### Table Cards

Cette table est composée de 3 champs : 
- c_id qui correspond à la clé primaire de la table 
- c_values
- c_color

>###### Explication des choix d'utilisations
 
 - De cette manière un id d'un carte correspond à une seule couleure et une seule valeur, ce qui permet d'identifier n'importe quelle cartes et ainsi pourvoir savoir par la suite la composition de la main des différents joueurs. 

 