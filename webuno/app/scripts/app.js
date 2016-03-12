'use strict';

/**
 * @ngdoc overview
 * @name unoApp
 * @description
 * # unoApp
 *
 * Main module of the application.
 */
angular
    .module('unoApp', [
        'ngAnimate',
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ui.router',
        'LocalStorageModule',
        'pascalprecht.translate'
    ])

    /**
     * Configuration pour récupérer les requêtes http
     *
     * Dans notre cas, incrémentation/décrémentation dans la variable isLoading du nombre de requêtes
     * http afin d'afficher un loader ou de bloquer des boutons lorsque des requêtes sont en cours
     */
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push(['$rootScope', function ($rootScope) {
            $rootScope.isLoading = 0;
            return {
                'request': function (config) {
                    $rootScope.isLoading++;
                    return config;
                },
                'requestError': function (rejection) {
                    $rootScope.isLoading = Math.max(0, $rootScope.isLoading - 1);
                    return rejection;
                },
                'response': function (response) {
                    $rootScope.isLoading = Math.max(0, $rootScope.isLoading - 1);
                    return response;
                },
                'responseError': function (rejection) {
                    $rootScope.isLoading = Math.max(0, $rootScope.isLoading - 1);
                    return rejection;
                }
            };
        }]);
    }])

    /**
     * On configure localStorage, il faut setter le type de storage qu'on veut utiliser
     */
    .config(['localStorageServiceProvider', function (localStorageServiceProvider) {
        localStorageServiceProvider.setStorageType('sessionStorage');
    }])

    /**
     * Configuration des routes
     *
     * ├ /login     => LoginController
     * ├ /register  => RegisterController
     * ├ /app       => AppController
     * ├──── /home      => HomeController
     * ├──── /start     => StartController
     * ├──── /room      => RoomController
     * ├──── /game      => GameController
     *
     * Route par défaut /login
     *
     */
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: 'views/login.html',
                controller: 'LoginController'
            })
            .state('register', {
                url: '/register',
                templateUrl: 'views/register.html',
                controller: 'RegisterController'
            })
            .state('app', {
                url: '/app',
                abstract: true,
                templateUrl: 'views/app.html',
                controller: 'AppController'
            })
            .state('app.home', {
                url: '/home',
                templateUrl: 'views/home.html',
                controller: 'HomeController'
            })
            .state('app.start', {
                url: '/start',
                templateUrl: 'views/start.html',
                controller: 'StartController'
            })
            .state('app.room', {
                url: '/room/:name',
                templateUrl: 'views/room.html',
                controller: 'RoomController'
            })
            .state('app.game', {
                url: '/game/:name',
                templateUrl: 'views/game.html',
                controller: 'GameController'
            });

        $urlRouterProvider.otherwise('/login');
    })

    /**
     * Configuration des langues (EN|FR)
     */
    .config(function ($translateProvider) {
        $translateProvider
            .translations('en', {
                EMAIL: 'Email',
                NAME: 'Name',
                PASSWORD: 'Password',
                OR: 'OR',
                LOGIN: 'Login',
                CONNECTGUEST: 'Connect Guest',
                REGISTER: 'Register',
                NOTACCOUNT: 'You don\'t have an account ?',
                ALREADYACCOUNT: 'You already have an account ?',
                STARTNEWGAME: 'Start new game',
                ALLGAMES: 'All games',
                NOGAMESSTARTED: 'There is no started games yet',
                STARTED: 'Started',
                WAITINGPLAYERS: 'Waiting for players',
                FULLPLAYERS: 'Full players',
                JOIN: 'Join',
                STARTGAME: 'Start game now',
                JOINED: 'joined',
                JOINGAME: 'join the game',
                CARD: 'card',
                TURN: 'It\'s {{username}} turn !',
                TURN_YOUR: 'It\'s your turn !',
                PICK_COLOR: 'Pick a color'
            })
            .translations('fr', {
                EMAIL: 'Email',
                NAME: 'Nom',
                PASSWORD: 'Mot de passe',
                OR: 'OU',
                LOGIN: 'Connexion',
                CONNECTGUEST: 'Connexion en tant qu\'invité',
                REGISTER: 'S\'inscrire',
                NOTACCOUNT: 'Tu n\'as pas de compte ?',
                ALREADYACCOUNT: 'Tu as déjà un compte ?',
                STARTNEWGAME: 'Créer une nouvelle partie',
                ALLGAMES: 'Toutes les parties',
                NOGAMESSTARTED: 'Il n\'y a pas encore de partie commencée',
                STARTED: 'Commencée',
                WAITINGPLAYERS: 'En attente de joueurs',
                FULLPLAYERS: 'Partie complète',
                JOIN: 'Rejoindre',
                STARTGAME: 'Commencer à jouer',
                JOINED: 'joueurs',
                JOINGAME: 'a rejoint la partie',
                CARD: 'carte',
                TURN: 'C\'est à {{username}} de jouer !',
                TURN_YOUR: 'C\'est à toi de jouer !',
                PICK_COLOR: 'Choisis ta couleur'
            })
            .preferredLanguage('en');
    })
;