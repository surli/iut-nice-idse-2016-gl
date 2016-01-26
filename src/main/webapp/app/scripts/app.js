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
        'ngRoute',
        'ngSanitize',
        'ui.router'
    ])
    .run(['$rootScope', '$location', function ($rootScope, $location) {
        $rootScope.$on('$routeChangeStart', function (event, currRoute, prevRoute) {
            // TODO $location
        });
    }])
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider
        // Ajout des routes inscription + connexion
            .state('app', {
                url: "/app",
                templateUrl: "views/app.html",
                controller: "AppController"
            })
            .state('app.start', {
                url: "/start",
                templateUrl: "views/start.html",
                controller: "StartController"
            })
            .state('app.game', {
                url: "/game",
                templateUrl: "views/game.html",
                controller: "GameController"
            });

        $urlRouterProvider.otherwise("/app/start");
    });
