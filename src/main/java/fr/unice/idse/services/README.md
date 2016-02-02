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
├── /game (GET/POST)
│   ├── /{gamename} (GET/PUT/DELETE)
│   │   ├── /command (GET/POST)
│   │   ├── /{pseudo} (GET/PUT)
├── /player (GET)
│   ├── /{pseudo} (GET)
```

Chaque route doit posseder un Token dans le header pour nom _token

__POST /uno/game__ CreateGame

Send :
```json
{
    "gamename":"uno",
    "pseudo":"john"
}
```
Return :

* 200 Ok
* 401 Invalid token
* 405 Missing or invalid parameters
* 500 Gamename already in use

__GET /uno/game__ GetListGames
 
 Return :
 ```json
{
    "games": [
        {
            "name":"uno",
            "state":0,
            "nbPlayer":2
        }
    ]
}
```
 
__PUT /uno/game/{gamename}__ AddPlayer

Send :
 ```json
{
    "pseudo":"bob"
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
		{"pseudo":"bob","nbCard":5},
		{"pseudo":"john","nbCard":5},
		{"pseudo":"marcel","nbCard":4}
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
    "pseudo": "John",
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

__GET /uno/game/{gamename}/{pseudo}__ (GetHand)

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

__PUT /uno/game/{gamename}/{pseudo}__ (PlayCard)

Send :
 ```json
{
    "posCard": 0
}
```

__PUT /uno/game/{gamename}/{pseudo}__ (PlayCard)

Send :
 ```json
{
    "posCard": 0
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
