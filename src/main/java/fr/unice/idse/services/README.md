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
* Jocelin  Heinen
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
├── /initializer
│   ├── /creategame (TO BE UPDATE)
├── /lobby
│   ├── /listgame (DONE)
│   ├── /listplayer
├── /game
│   ├── /{gamename}
│   │   ├── /addplayer (DONE)
│   │   ├── /begingame
│   │   ├── /gamestate (DONE)
│   │   ├── /actualplayer
│   │   ├── /updated 
│   │   ├── /{pseudo}
│   │   │   ├── /hand 
├── /player
│   ├── /{pseudo}
```


__POST /uno/initializer/creategame (fait)__

Send :
```json
{
    "_token":"123456789",
    "gamename":"uno",
    "pseudo":"john"
}
```
Return :

* 200 Ok
* 401 Invalid token
* 405 Missing or invalid parameters
* 500 Gamename already in use

__GET /uno/lobby/listgame (fait)__
 
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
 
__GET /uno/lobby/listplayer__
 
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

__POST /uno/game/{gamename}/addplayer (fait)__

Send :
 ```json
{
    "_token":"123456789",
    "pseudo":"bob",
}
```

__POST /uno/game/{gamename}/begingame__

Send :
 ```json
{
    "_token":"123456789",
    "pseudo":"john",
}
```

__GET /uno/game/{gamename}/gamestate (fait)__

Return :
 ```json
{
    "state":0,
}
```

__GET /uno/game/{gamename}/actualplayer__

Return :
 ```json
{
    "pseudo":"john",
}
```

__GET /uno/game/{gamename}/updated__

Return :
 ```json
{
    "update":true,
}
```

__GET /uno/game/{pseudo}/hand__

Return :
 ```json
{
    "cartes": [
        {
            "number":2,
            "familly":"As"
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
