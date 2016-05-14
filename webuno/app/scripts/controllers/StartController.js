'use strict';

angular.module('unoApp')
    /**
     * Contrôleur StartController de la route /app/start
     * Gère la création d'une partie
     */
    .controller('StartController', ['$rootScope', '$scope', '$state', '$http', 'Game', function ($rootScope, $scope, $state, $http, Game) {
        // Initialisation du nombre de joueurs à 2
        $scope.nbPlayers = '2';
        $scope.alternative = 'default';
        $rootScope.callbackHome = false;

        $scope.valueAlternatives = {
            'default' : 'Règle par défaut',
            'regle8' : 'Règle de la carte 8'
        };

        Game.getAlternatives(function (response) {
            $scope.alternatives = response.alternatives;
        });

        // Fonction qui permet de créer une nouvelle partie
        // Cette fonction est appelée à l'envoi du formulaire html
        $scope.goGame = function () {
            // Si le nom de la partie est saisi, qu'il fait plus de 3 caractères
            // et qu'on a le nom de l'utilisateur connecté alors
            // sinon on affiche l'erreur correspondante
            if ($scope.game && $scope.game.length > 3 && $scope.user.name) {
                // Utilisation du service Game pour créer une nouvelle partie
                Game.createGame($scope.game, $scope.nbPlayers, $scope.alternative, function() {
                    $state.go('app.room', { name: $scope.game });
                });
            } else {
                $rootScope.error = '4 characters minimum required to name the game';
            }
        };
    }]);
