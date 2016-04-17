'use strict';

angular.module('unoApp')
    /**
     * Contrôleur RoomController de la route /app/room
     * Gère les joueurs entrant dans la room avant de commencer la partie
     */
    .controller('RoomController', ['$rootScope', '$scope', '$state', '$stateParams', '$http', '$timeout', 'Game', function ($rootScope, $scope, $state, $stateParams, $http, $timeout, Game) {
        // On récupère id de la room passé en paramètre dans l'url
        $rootScope.gameName = $stateParams.name;
        var timeoutStateGame;

        // Utilisation du service Game pour récupérer l'état de la partie
        Game.getGame($rootScope.gameName, function (data) {
            $scope.game = data;
            $scope.requestStateGame();
        }, function () {
            $timeout.cancel(timeoutStateGame);
            $state.go('login');
        });

        // Fonction qui permet de requêter toutes les 2 secondes sur l'état de la partie
        $scope.requestStateGame = function () {
            // Timer de 2 secondes
            timeoutStateGame = $timeout(function () {
                // Utilisation du service Game qui permet de récupérer l'état de la partie
                Game.getGame($rootScope.gameName, function (data) {
                    $scope.game = data;
                    // Si l'état est à true
                    // alors la partie est lancée et les joueurs sont redirigés vers la partie commencée
                    // sinon la fonction s'appelle elle même
                    if ($scope.game.state) {
                        $state.go('app.game', {name: $rootScope.gameName});
                    } else {
                        $scope.requestStateGame();
                    }
                }, function () {
                    $timeout.cancel(timeoutStateGame);
                    $state.go('login');
                });
            }, 2000);
        };

        // Fonction qui permet de lancer la partie
        $scope.startGameNow = function () {
            // Utilisation du service Game pour lancer une partie
            // Le bouton qui lance cette fonction n'apparaît que si le nombre de joueurs maximum est atteint
            // et que le joueur connecté est le créateur de la partie
            Game.startGame($rootScope.gameName, function () {
                Game.getGame($rootScope.gameName, function (data) {
                    $scope.game = data;
                    $state.go('app.game', {name: $rootScope.gameName});
                });
            });
        };

        $rootScope.callbackHome = function() {
            $timeout.cancel(timeoutStateGame);
            if ($scope.game.state === false && !$rootScope.logout) {
                Game.quitRoom($rootScope.gameName);
            }
        };

        // Évènement qui permet de stopper le timer et quitter la room quand on quitte le contrôleur RoomController
        /*
        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutStateGame);
            if ($scope.game.state === false && !$rootScope.logout) {
                Game.quitRoom($rootScope.gameName);
            }
        });
        */

        // Évènement qui permet de quitter la room quand on ferme l'onglet contenant la room
        /* TODO : ne fonctionne pas - quand on actualise tout casse
        window.onbeforeunload = function () {
            if (!$rootScope.logout) {
                Game.quitRoom($rootScope.gameName);
            }
        };
        */
    }]);
