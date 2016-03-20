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
Elle propose des méthodes pour accéder et changer les éléments qui la contitues ainsi que des méthodes de comparaison. Il se peut que certaine de ces méthodes n'ont pas été nécessairement appelé mais elles existe.

### Package player

Un joueur peut être humain ou IA, il est défini de manière général par un nom(string), une main (list de carte), un score(int), un token(string), et d'autre élément pouvant être utile au developement du composant.

### Package regle

Certaines cartes peuvent avoir un effet au cour de la partie.
La classe EffectCard permet attribuer un effet sur la valeur d'une carte.
Elle a besoin de connaitre le plateau de jeu(Board) sur lequel elle interagie ainsi que la valeur de la carte sur lequel elle sera affectée pour être initialiser.

```
Pour utiliser l'effect d'une carte, il suffit d'appeler la méthode void action().

Si on est dans le cas d'un chagement de couleur de jeu la méthode void changeColor(Color couleur) peut être appelé prenant en paramètre la nouvelle couleur de jeu.
Pour savoi si l'effect de la carte correspond à un changement de couleur une méthode booelan isColorChangingCard() existe retournant true si c'est le cas.

La méthode boolean getEffect()(possibilité de renommer la méthode) renvoi true si le compteur du nombre de carte à piocher à changer et n'est plus à sa valeur de 1 par défault.
Dans ce Cas il est nécessaire d'utiliser la méthode void effect() arpès avoir changer de joueur pour appliquer la règle sur le joueur actuel.
```

### Class Alternative

C'est la classe qui permet de répertorier toute les règles qui est initialiser dans le board et qui propose des accesseurs aux EffectCards.

### Class Board && Game

C'est ici que se déroule tout les actions du jeux.

### Class Deck && Stack

### Class Model