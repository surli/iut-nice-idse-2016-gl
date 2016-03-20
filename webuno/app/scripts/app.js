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
            .useStaticFilesLoader({
                prefix: './i18n/',
                suffix: '.json'
            })
            .useSanitizeValueStrategy(null)
            .registerAvailableLanguageKeys(['fr', 'en'], {
                'fr_*': 'fr',
                'en_*': 'en'
            })
            .determinePreferredLanguage('fr');
    })
    .run(function($rootScope, $translate) {
        $rootScope.lang = 'fr';
        // Fonction qui permet de changer la langue
        $rootScope.changeLanguage = function (langKey) {
            // Utilisation de la dépendance $translate pour changer la langue de l'app
            $rootScope.lang = langKey;
            $translate.use(langKey);
        };
    })
;
