# Base de donnée du Uno

### Composition de la base de donée 

La base de donnée est actuellement composée de 11 tables :

- Games
- Statut_users
- Users
- Cards
- Matchs
- Turns
- Players_in_games
- Hand_players_in _game
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

 ### Table Matchs

 Cette table est composée de 2 champs :
 - m_id
 - m_g_id

 >###### Explication des choix d'utilisations : 

Les deux champs composent la clé primaire composite. Cette table permet d'inserer la notion de manche. Une partie est composée d'une ou plusieurs manches. Cette table sert de table de transition avec la table turn , hands_players_in_game, deck, stack

 ### Table Turns

Cette table est composée de 4 champs : 
- t_id qui correspond à la clé primaire de la table 
- t_m_id
- t_sens
- id_user_ready

>###### Explication des choix d'utilisations : 

Un tour correspond a une action de jeu d'un joueur. Ainsi si le joueur 1 joue cela correspond au tour 1, lorsque le joueur 2 joue cela correspond au tour 2, etc... De cette manière là, pour une partie donnée, une manché donnée, on enregistre le sens du jeu à un joueur donnée. Cela permet de reconstituer par la suite la partie lors d'une sauvegarde. 

- t_sens est pour le moment un ENUM. 

### Table hands_players_in_game

Cette table est composée 4 champs : 
- h_id_match
- h_id_user
- h_id_cards
- h_tour

>###### Explication des choix d'utilisations : 

La clé primaire de cette table est une clé composite de h_id_match,h_id_user,h_id_card. 
Ainsi de cette manière il est possible d'enregistrer a chaque tour pour un joueur, une manche, les cartes en main d'un joueur. Pour la sauvegarde cela permet de reconstituer la main des joueurs pour une partie et une manche. 

### Table payer_in_game

Cette table est composée de 3 champs : 
- p_g_id
- p_id_user
- p_position

>###### Explication des choix d'utilisations : 

La clé primaire de cette table est une clé composite de p_g_id,p_id_user. 
Cette table permet d'enregistrer les utilisateurs présents dans une partie. Le nombre de joueur dans cette table pour une partie donnée ne pourra pas dépasser le nombre de joueur maximum saisie précédement. 
De plus, l'enregistrement de la position permet d'avoir le joueur qui est entrain de jouer ainsi que de favoriser l'enregistrement du sens, ou de permettre le départ d'un joueur et son remplacement à une position donnée.  


### Table Deck 

Cette table est composée de 3 champs : 
- d_t_id
- d_m_id
- d_c_id 

>###### Explication des choix d'utilisations : 

La clé primaire de cette table est une clé composite de d_t_id,d_m_id. 

Cette table permet l'enregistrement des cartes qui ont été piochées lors d'un tour. Ainsi, de cette manière il est possible de reconstituer la partie en sachant qui a piocher quoi à quel tour, de quel manche de quel partie. 

### Table Stack

Cette table est composée de 3 champs : 
- s_t_id
- s_m_id
- s_c_id 

>###### Explication des choix d'utilisations : 

La clé primaire de cette table est une clé composite de s_t_id,s_m_id. 

Cette table permet l'enregistrement des cartes qui ont été défaussé lors d'un tour. Ainsi, de cette manière il est possible de reconstituer la partie en sachant qui a défausser quoi à quel tour, de quel manche de quel partie. 
