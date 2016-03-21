# UNO Moteur

### Table of contents

- [Contributors](#contributors)
- [Introduction] (#introduction)
- [Composition] (#composition)
- [Package card] (#package-card)
- [Package player] (#package-player)
- [Package regle] (#package-regle)
- [Class Alternative] (#class-alternative)
- [Class Board && Game] (#class-board&&game)
- [Class Deck && Stack] (#class-deck&&stack)
- [class Model] (#class-model)

### Contributors

* Baptiste Etienne
* Kevin Detti
* Pierre-Antoine Charpentier
* Regis Parpex
* Stéphanie Carrié
* Tom Phily

### Introduction

Ce Readme a pour but d'expliquer comment utiliser le moteur de jeu se trouvant dans le package model avec ses classes et ses méthodes.

### Composition

Le package model est constitué de 3 sous packages :
```
card
player
regle
```
Et des classes Alternative, Board, Deck, Game, Main(pour un lancement en consol d'une partie), Model et Stack.

### Package card

Une carte est définie par sa couleur(enum Color) et sa valeur(enum Value).
Elle propose des méthodes pour accéder et changer les éléments qui la contitues ainsi que des méthodes de comparaison. Il se peut que certaine de ces méthodes n'ont pas été nécessairement appelé mais elles existent.

### Package player

Un joueur peut être humain ou IA, il est défini de manière générale par un nom(string), une main (list de carte), un score(int), un token(string), et d'autres éléments pouvant être utiles au developement du composant.

### Package regle

Certaines cartes peuvent avoir un effet au cour de la partie.
La classe EffectCard permet d'attribuer un effet sur la valeur d'une carte.
Elle a besoin de connaitre le plateau de jeu(Board) sur lequel elle intéragie ainsi que la valeur de la carte sur laquelle elle sera affectée pour être initialisée.

```
Pour utiliser l'effet d'une carte, il suffit d'appeler la méthode void action().

Si on est dans le cas d'un changement de couleur de jeu la méthode void changeColor(Color couleur) peut être appelée et elle prendra en paramètre la nouvelle couleur de jeu.
Pour savoir si l'effet de la carte correspond à un changement de couleur une méthode booelan isColorChangingCard() existe retournant true si c'est le cas.

La méthode boolean getEffect()(possibilité de renommer la méthode) renvoie true si le compteur du nombre de carte à piocher a changé et n'est plus à sa valeur de 1 par défault.
Dans ce Cas il est nécessaire d'utiliser la méthode void effect() arpès avoir changé de joueur pour appliquer la règle sur le joueur actuel.
```

### Class Alternative

C'est la classe qui permet de répertorier toutes les règles initialisées dans le board et qui propose des accesseurs aux EffectCards.

### Class Board && Game

C'est ici que se déroule tout les actions du jeux.

Ajouter des joueurs dans la partie en utilisant addPlayer(Player player) prennant un joueur en paramètre.
Pour lancer la partie utiliser la méthode boolean start() de Game retournant true si la partie a bien été initialisée.
> Todo : Ajouter un constructeur avec Alternative pour choisir la variante du jeu.

Après avoir lancé la partie et qu'elle a bien été initialisée, on peut utiliser les méthodes de Board suivante :
 - askPlayerCanPlay(Player player) renvoie true si le joueur en paramètre est le joueur actuel et qu'il peut jouer une carte de sa main.
 - playableCards() retourne la liste des cartes que le joueur actuel peut jouer.
 - poseCard(Card ) pose la carte de la main du joueur actuel dans la fosse si elle peut être jouée sinon rien. Met fin à la partie si le joueur à plus de carte en main.
 - drawCard() le joueur actuel pioche le nombre de carte que le compteur cptDrawCard indique.
 - nextPlayer() passe au joueur suivant en respectant le sens du jeu.

### Class Deck && Stack

Composé de listes de cartes, ces deux classes représentent la fosse et la pioche.
Elles sont presque identiques à quelques différences près, il serait intéressant de proposer une classe mère et de les mettre dans un sous package.

La Pioche propose la méthode vode initDeck() a appelé en début de partie pour remplir la pioche de toutes les cartes composant le Uno et la mélanger.
Lorsque la pioche est vide, il faut utiliser la méthode void reassembleDeck(Stack stack) prenant en paramètre la fosse du board pour reformer la pioche.

### Class Model