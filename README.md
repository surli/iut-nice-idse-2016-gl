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

###Constitution des groupes
>######Chef de projet : Jérémie Elbaz

####Moteur
>Chef de groupe : Baptiste Etienne

- Kevin Detti
- Tom Phily
- Pierre-Antoire Charpentier
- Stéphanie Carrier
- Nassim Omrani

###IHM
>Chef de groupe : Jérémy Froment

- Teva Locandro
- Nicolas Claisse

###Rest
>Chef de groupe : Damien Clemenceau

- Jérémie Elbaz
- Jocelin Heinen
Lien du README du groupe : [Cliquez ici](/stash/projects/LPA/repos/projet-s2/src/main/java/fr/unice/idse/services/README.md)

###Base de donnée
>Chef de groupe : Marina Helie-Zadeh

- Ugo Paneccasio
- Pierre Echardour
- Medhi Ibnettalib
- Regis Parpex

###Spécification du projet
- Utilisation de Token pour assurer une sécurité en REST dans le header de la requête.
- La partie peut se lancer uniquement quand toute la partie est complète.
- Seulement l'hôte peut lancer la partie.
- Les parties peuvent être comprises entre 2 et 6 joueurs. (Actuellement les parties créées sont à 4 joueurs.)

