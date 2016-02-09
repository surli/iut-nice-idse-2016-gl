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
│   │   ├── /{playerName} 
│   │   │   ├── GET     Retoune la main du joueur
│   │   │   ├── POST    Pioche une carte
│   │   │   ├── PUT     Joue une carte
├── /player 
│   ├── GET             Retourne la liste des joueurs
│   ├── /{playerName} 
│   │   ├── GET         Retourne les informations du joueur
├── /auth
│   ├── POST            Authentifie un Guest en renvoyant un token
```

Chaque route doit posseder un Token dans le header pour nom _token

__POST /uno/game__ CreateGame

Send :
```json
{
    "gamename":"uno",
    "playerName":"john"
}
```
Return :

* 200 Ok
* 401 Invalid token
* 405 Missing or invalid parameters
* 500 Gamename already in use

__GET /uno/game__ GetListGames
 
 Return 200 Ok :
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
 
__PUT /uno/game/{gamename}__ AddPlayer

Send :
 ```json
{
    "playerName":"bob"
}
```

__GET /uno/game/{gamename}__ GetGameState

> TODO

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

__DELETE /uno/game/{gamename}__ GetGameState

> TODO

Return Ok

__GET /uno/game/{gamename}/command__ CurrentPlayer

Return the current player of the game 

There are three type of return :

1. Response 200 Ok
 ```json
{
    "playerName": "John",
}
```

2. Response 401 Unauthorized
 ```json
{
    "error": "Game has not begin",
}
```

__PUT /uno/game/{gamename}/command__ BeginGame

> TODO

Return Ok

__GET /uno/game/{gamename}/updated__ GetUpdate

Return :
 ```json
{
    "update":true,
}
```

__GET /uno/game/{gamename}/{playerName}__ (GetHand)

Return :
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

__POST /uno/game/{gamename}/{playerName}__ (PickACard)

Return Ok

__PUT /uno/game/{gamename}/{playerName}__ (PlayCard)

Send :
 ```json
{
    "value": 0,
	"color": "Blue",
	"actionCard": null
}
```

__GET /uno/player__ GetListPlayers
 
  Return :
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


### Running test

Launch the server then you can now run the test

### TODO

* Complete this README.md
* Implements these routes
* List all routes with they return value