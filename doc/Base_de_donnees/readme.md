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

