# Uno

###Installation du projet
>######Attention
Le projet est en Java8

Pour cloner le projet : 
```
git clone https://{votrepseudo}@atlas.i3s.unice.fr/stash/scm/lpa/projet-s2.git
```



>Pour installer et tout lancer : 

######Sous linux et windows : 
```
build.sh
```
######Sous Mac OS : 
```
sh build.sh
```

######Installation de la base de donnée

>######Attention
Pré-requis : pouvoir utiliser la commande `mysql` dans un terminal

```
database.sh [login] [password]
```

>Pour lancer installer les plugins et lancer le serveur REST :

######Sous Eclipse : 

```
Run > Run Configurations > clique droit sur Maven Build > New
```

- Goals : `install jetty:run`
- Base directory : `${workspace_loc:/projet-s2`

######Sous IntelliJ IDEA : 
```
Run > Edit Configurations ... > clique sur "+" > Maven
```
- Command Line : `install jetty:run`
- Working directory : `Lien vers le dossier projet-s2`

>######Note
Lancez les tests avant de commit. Une commande simple (pareil que pour lancer le serveur REST)
Dans goals ou Command line : `install test`

### Documents

Tous les documents de chaque groupe se trouve dans le repertoire doc à la racine.

###Release

>######Version 0.8

- Moteur de recherche pour l'admin (utilisateur)
- Test de l'IA
- Test sauvegarde / relance
- Correction de bug

>######Version 0.7.1

- Correction bug dans l'IHM
- Ajout du ppt pour la présentation du 19/04/2016
- Ajout du diagramme de classe

>######Version 0.7

- Pouvoir recuperer tous les utilisateurs de la BDD (manque le filtre)
- Modifier le rang d'un utilisateur (admin)
- Pouvoir bannir un utilisateur (admin)
- Sauvegarde / relance d'une partie (pas encore tester)
- Système de variante oppérationnel (pas encore implémenter dans REST/IHM)
- Ajout d'un connecteur pour la base de donnée (SQLite)
- Ajout des logs (pas encore implémenté de partout)
- Refactorisation des class Game / Board en une seule class (Game)
- Correction de bug

>######Version 0.6.1

- Correction build.sh (ajout du --force car problème de droit)

>######Version 0.6

- Admin 
    - Voir les parties
    - Voir les joueurs dans une partie
    - Détruire une partie
- Refacto Bdd
- Amélioration script bdd
- Correction de quelques bugs sur l'IHM
- Variante 7

>######Version 0.5

- Jeu basique oppérationnel
- Ajout des rangs dans les utilisateurs (1 Bot, 2 Guest, 3 Register, 4 Admin)
- Sauvegarder une partie (pas encore implémenter dans IHM / REST) 
- Ajout de l'IA medium
- Refactorisation niveau REST
- Creation de la vue admin
- Script pour installation de la base de donnée

>######Version 0.4

- Multi-langue
- Pouvoir quitter une room
- Pouvoir quitter une partie en cours
- Se deconnecter 
- Changement de couleur (+4 et carte changement couleur)
- Ajout des actions des cartes spéciales
- IA Basique
- Refractoring IHM/REST


>######Version 0.3.1

- Correction de la distribution des cartes
- Correction de quelques bugs dans l'interface web
- Correction du pom.xml

>######Version 0.3

- Ajout des routes pour l'inscription / connexion avec ses identifiants
- Mis en place de la base de donnée
- Ajout des actions dans le moteur
    - Action du stop
    - Changement de sens
    - Changement de couleur
- Se connecter avec ses identifiants sur l'ihm
- Pouvoir s'inscrire

>######Version 0.2

- Pouvoir jouer une partie via console (que les cartes basiques)
- Se connecter en Guest
- Voir la liste des parties dans l'ihm
- Créer une partie dans l'ihm
- Rejoindre une partie dans l'ihm
- Commencer la partie une fois que tous les joueurs sont présents

>######Version 0.1

- Initialiser une partie en REST
- Developpement de certaine vue
- Moteur peut initialiser une partie


###Constitution des groupes
>######Chef de projet : Jérémie Elbaz

####Moteur
>Chef de groupe : Baptiste Etienne

- Kevin Detti
- Tom Phily
- Pierre-Antoire Charpentier
- Stéphanie Carrier
- Nassim Omrani
- Regis Parpex (Partie IA)

###IHM
>Chef de groupe : Jérémy Froment

- Teva Locandro
- Nicolas Claisse

###Rest
>Chef de groupe : Damien Clemenceau

- Jérémie Elbaz
- Jocelin Heinen

###Base de donnée
>Chef de groupe : Pierre Echardour 

- Ugo Paneccasio
- Marina Helie-Zadeh
- Medhi Ibnettalib

###Spécification du projet
- Utilisation de Token pour assurer une sécurité en REST dans le header de la requête.
- La partie peut se lancer uniquement quand toute la partie est remplie.
- Seulement l'hôte peut lancer la partie.
- Les parties peuvent être comprises entre 2 et 6 joueurs.
- Chargement des données sur la vue toutes les 5 secondes.

