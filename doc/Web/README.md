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

Pour réalisé la partie IHM de ce projet nous avons décidé d'utilisées les outils suivant:

    - AngularJS : (Framework Javascript)
        * C'est un framework JS qui utilise de bon concepts et bonnes pratiques du web ( MVC, Data Binding, Injections de dépendances, Manipulation du DOM avec des diretives)
    - GruntJS : (task runner)
        * Automatise les tâches, réduits les risques d'erreurs, en bref nous évites de faire des actions répétitives
    - Bower :
    - Bootstrap : (Framework CSS)
        * Librairies de CSS qui contient une panoplie de composants et qui nous permet d'aller plus vite dans la construction du HTML.
    - Compass : (Framework CSS basé sur Sass)
        * Permet d'écrire des lignes de CSS avancés et nous accordes une certaine facilité de developpement au niveau du CSS.
    - Sass : (Language CSS)
        *  Fournit les variables, les mixins, les fonctions et toute l’API basique permettant de coder
    - Karma & Jahsmin : (Tests)
        * Karma qui va déclencher nos suites de tests, la maintenir en exécution et vous permettre de l'organiser et Jahsmin est framework de tests compatible avec Karma
    
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
├── /dist                -> contient le webapp qui sera build
├── /test                -> contient les tests pour chaque controller
├── /WEB-INF             -> contient le web.xml qui configure l'app une fois le serveur jetty lancé (url du /rest, CORS, etc...)
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