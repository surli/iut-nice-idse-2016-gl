# Uno

###Installation du projet
>######Attention
Le projet est en Java8

Pour cloner le projet : 
```
git clone https://{votrepseudo}@atlas.i3s.unice.fr/stash/scm/lpa/projet-s2.git
```

Pour lancer installer les plugins et lancer le serveur REST :

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

>######Version 0.3

- Ajout des routes pour l'inscription / connexion avec ses identifiants
- Mis en place de la base de donnée
- Ajout des actions dans le moteur
    - Action du stop
    - Changement de sens
    - Changement de couleur

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

