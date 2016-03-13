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
        Game.getGame($rootScope.gameName)
            .then(function (response) {
                if (response.data.error) {
                    $timeout.cancel(timeoutStateGame);
                    $state.go('login');
                } else {
                    $scope.game = response.data;
                    $scope.requestStateGame();
                }
            }, function (error) {
                if (error.data.error) {
                    $timeout.cancel(timeoutStateGame);
                    $state.go('login');
                } else {
                    $scope.requestStateGame();
                }
            });

        // Fonction qui permet de requêter toutes les 2 secondes sur l'état de la partie
        $scope.requestStateGame = function () {
            // Timer de 2 secondes
            timeoutStateGame = $timeout(function () {
                // Utilisation du service Game qui permet de récupérer l'état de la partie
                Game.getGame($rootScope.gameName)
                    .then(function (response) {
                        $scope.game = response.data;
                        // Si l'état est à true
                        // alors la partie est lancée et les joueurs sont redirigés vers la partie commencée
                        // sinon la fonction s'appelle elle même
                        if ($scope.game.state) {
                            $state.go('app.game', {name: $rootScope.gameName});
                        } else {
                            $scope.requestStateGame();
                        }
                    }, function () {
                        $scope.requestStateGame();
                    });
            }, 2000);
        };

        // Fonction qui permet de lancer la partie
        $scope.startGameNow = function () {
            // Utilisation du service Game pour lancer une partie
            // Le bouton qui lance cette fonction n'apparaît que si le nombre de joueurs maximum est atteint
            // et que le joueur connecté est le créateur de la partie
            // Si le statut est 200 alors le créateur de la partie est redirigé vers la partie commencée
            // sinon un message d'erreur apparait
            Game.startGame($rootScope.gameName)
                .then(function (response) {
                    switch (response.status) {
                        case 200 :
                            if (response.data.status) {
                                Game.getGame($rootScope.gameName)
                                    .then(function(response) {
                                        $scope.game = response.data;
                                        $state.go('app.game', {name: $rootScope.gameName});
                                    });
                            } else {
                                console.error(response);
                            }
                            break;
                        default:
                            console.error(response);
                    }
                });
        };

        // Évènement qui permet de stopper le timer et quitter la room quand on quitte le contrôleur RoomController
        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutStateGame);
            if ($scope.game.state === false && !$rootScope.logout) {
                Game.quitRoom($rootScope.gameName);
            }
        });

        // Évènement qui permet de quitter la room quand on ferme l'onglet contenant la room
        window.onbeforeunload = function () {
            if (!$rootScope.logout) {
                Game.quitRoom($rootScope.gameName);
            }
        };
    }]);
