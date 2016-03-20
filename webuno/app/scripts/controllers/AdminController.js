'use strict';

angular.module('unoApp')
    /**
     * Contrôleur global AppController de la route /app
     * Gère l'ensemble de l'application une fois un utilisateur connecté
     */
    .controller('AdminController', ['$rootScope', '$scope', '$state', 'Auth', 'Game', function ($rootScope, $scope, $state, Auth, Game) {
        if (!Auth.isAdmin()) {
            $state.go('login');
        }

        Game.getAllGames(function(data) {
           $scope.games = data;
        });
    }]);