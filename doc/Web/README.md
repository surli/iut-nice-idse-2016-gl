# UNO Interface Web [IHM]

### Table of contents

- [Contributors](#contributors)
- [Introduction](#introduction)
- [Install](#install)
- [Launch](#launch)
- [Running test](#running-test)
- [TODO](#todo)
- [Documentation](#Documentation)

### Contributors

* Jeremy Froment
* Teva Locandro
* Nicolas Claisse

### Introduction 

##### Arbo structure

```
/webuno
├── /app 
│
│   ├── /images         -> contient les images
│   ├── /scripts        -> contient les appels des routes de l'apiREST, les controllers des vues et les fonctions permettant de pouvoir jouer au UNO et faire fonctionner l'application       
│   ├── /styles         -> contient les styles
│   ├── /views          -> contient les vues de l'application web.
│
├── /bower_components    -> composants permettant de faire fonctionner bower
├── /node_modules        -> tout les modules permettant au bon fonctionnement de npm
├── /dist                -> 
├── /test                -> contient les tests pour chaque controller
├── /WEB-INF             ->
├── bower.json           -> contient les packages pour bower
├── Gruntfile.js         -> contient les packages pour grunt
├── package.json         -> contient les packages pour npm
```

#### Install

Run this command for install npm dependencies :
```
npm install
```

#### Development

Run this command for start development :
```
npm start
```

#### Testing

Run this command for start run the unit tests with karma :
```
npm test
```

#### Build

Running this command for build (run build.sh) :
```
npm run build
```

### TODO

* Complete this README
* Create a functional interface
* Users can play UNO

### Documentation

#### Yeoman

https://github.com/yeoman/generator-angular#readme

#### AngularJS

https://docs.angularjs.org/guide/

#### GruntJs 

http://gruntjs.com/getting-started