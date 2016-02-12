# Base de donnée

## Explication de la base de donnée

# 1) Une table utilisateur 

Cette table a été crée afin de pouvoir enregistrer un nouvel utilisateur, ou de permettre la connexion de celui ci à l'application

La table utilisateur est composée de la manière suivante : 
- Un pseudo au format Varchar, qui constitue la clé primaire de la table; Ce pseudo sera donc unique.
- Un email au format Varchar, qui comporte une contrainte unique. Ainsi l'adresse mail ne pourra être utilisé que pour un seul pseudo
- un passeword au format Varchar d'une longue maximal de 64 caratères afin de permettre un encodage en Sha256.

# 2) Une table Game 

Cette table a été crée afin de pouvoir recevoir l'enregistrement d'une partie. Pour le moment elle est composée de : 
- Un nom unique permettant de l'identifier
- g_player qui recevra un tableau de joueur, à savoir les quatres joueurs en cours dans la partie
- g_hands qui recevra un tableau de carte correspondant à la main des joueurs. Il reste encore à définir le nombre maximal de carte que pourra posseder un joueur. 