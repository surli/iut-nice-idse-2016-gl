# UNO Moteur

### Table of contents

- [Contributors](#contributors)
- [Introduction] (#introduction)
- [Composition] (#composition)
- [Package card] (#package-card)
- [Package player] (#package-player)
- [Package regle] (#package-regle)

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