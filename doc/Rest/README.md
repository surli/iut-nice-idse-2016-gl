# UNO Web service

### Table of contents

- [Contributors](#contributors)
- [Introduction](#introduction)
- [Install](#install)
- [Launch](#launch)
- [Routes](#routes)
- [Running test](#running-test)
- [TODO](#todo)

### Contributors

* Jeremie Elbaz
* Jocelin Heinen
* Damien Clemenceau

### Introduction

> TODO : Add a better introduction

This WebService use jersey who provide a REST services. 

### Install

On eclipse right click on your project then _run as_ and _Maven Build_ in goals write :
```
install jetty:run
```

### Launch

On eclipse right click on your project then _run as_ and _Maven Build_ in goals write :
```
jetty:run
```

### Routes

##### Routes structure
```
/uno
├── /game 
│   ├── GET             Liste des parties
│   ├── POST            Créer une partie
│   ├── /{gamename} 
│   │   ├── GET         Retourne l'état de la game
│   │   ├── PUT         Ajoute un joueur dans la partie
│   │   ├── DELETE      Supprime une partie
│   │   ├── /command
│   │   │   ├── GET     Retourne le joueur courant
│   │   │   ├── PUT     Lance une partie (Que l'host)
│   │   ├── /updated
│   │   │   ├── GET     Retourne si une mise a jour a eu lieu
│   │   ├── /{playerName} 
│   │   │   ├── GET     Retoune la main du joueur
│   │   │   ├── POST    Pioche une carte
│   │   │   ├── PUT     Joue une carte
│   │   │   ├── DELETE  Retire un joueur d'une partie
├── /player 
│   ├── GET             Retourne la liste des utilisateurs connecté 
│   ├── POST            Ajoute un utilisateur
│   ├── CONNECT         Connecte un utilisateur
│   ├── /{playerName}
│   │   ├── GET         Retourne les informations de l'utilisateur
│   │   ├── POST        Authentifie un Guest en renvoyant un token
│   │   ├── DELETE      Deconnecte l'utilisateurs
│   │   ├── PUT         Mise a jouer des informations de l'utilisateur
├── /auth
│   ├── POST            Authentifie un Guest en renvoyant un token
│   ├── PUT             Authentifie un joueur avec ses identifiants
```

Chaque route doit posseder un Token dans le header pour nom _token

__GET /uno/game__ GetListGames
 
Return:

* 200 Ok :
 ```json
{
    "games": [
        {
            "gamename":"uno",
            "state":false,
            "numberplayer":2,
            "maxplayer":3
        }
    ]
}
```
* 404 Missing token
* 405 Invalid token



__POST /uno/game__ CreateGame

Send :
```json
{
    "gamename":"uno",
    "playerName":"john"
    "numberplayers:3
}
```
Return :

* 200 Ok
* 404 Missing token
* 405 Missing or invalid parameters or invalid token
* 500 Gamename already in use
 
__GET /uno/game/{gamename}__ GetStateGame

Send :
 ```json
{
	"state":1,
	"player": 
	[
		{"playerName":"bob","nbCard":5},
		{"playerName":"john","nbCard":5},
		{"playerName":"marcel","nbCard":4}
	]
}
```

Return :
* 200 Ok
* 404 Missing token
* 405 Missing game or invalid token

__PUT /uno/game/{gamename}__ AddPlayer

Send :
 ```json
{
    "playerName":"bob"
}
```

Return :
* 200 Ok
* 404 Missing token
* 405 Missing or invalid parameters or invalid token
* 500 Game already begun 

__DELETE /uno/game/{gamename}__ GetGameState

Return:

* 200 Ok
* 404 Missing token
* 405 Invalid token

__GET /uno/game/{gamename}/command__ ActualPlayer

Return :

* Response 200 Ok
 ```json
{
    "playerName": "John",
}
```

* Response 401 Unauthorized
 ```json
{
    "error": "Game has not begin",
}
```

* Response 405 Method Not Allowed
 ```json
{
    "error": "Invalid token",
}
```

* Response 422 Unprocessable entity
 ```json
{
    "error": "No current player has been set",
}
```

__PUT /uno/game/{gamename}/command__ BeginGame

Return:

* 200 Ok
* 404 Missing game or token
* 405 Invalid parameters

__GET /uno/game/{gamename}/updated__ GetUpdate

Return :

* 200 Ok
 ```json
{
    "update":true,
}
```

__GET /uno/game/{gamename}/{playerName}__ GetHand

Return :

* 200 Ok
 ```json
{
    "cards": [
        {
            "number":2,
            "familly":"As"
        }
    ]
}
```
* 404 Missing token
* 405 Invalide player 

__POST /uno/game/{gamename}/{playerName}__ PickACard

Return :

* 200 Ok
 ```json
{
	"return":true
}
```
* 404 Missing game or token
* 405 Invalid token, game not bagan, wrong turn or invalid card

__PUT /uno/game/{gamename}/{playerName}__ PlayCard

Return :

* 200 Ok
 ```json
{
    "value": 0,
	"color": "Blue",
	"actionCard": null
}
```

* 404 Missing token
* 405 Invalid parameter

__GET /uno/player__ GetListPlayers
 
Return :
* 200 Ok
 ```json
{
    "players": [
        {
            "pseudo":"john",
            "email":"john@mail.com",
        }
    ]
}
```

__GET /uno/player__ GetPlayers
 
Return :

* 200 Ok
 ```json
{
	"pseudo":"john",
	"email":"john@mail.com"
}
```
* 405 Invalid player

### Running test

Launch the server then you can now run the test

### TODO

* Complete this README.md
* Implements these routes
* List all routes with they return value