'use strict';

angular.module('unoApp')
    /**
     * Contrôleur GameController de la route /app/game
     * Gère le jeu/la partie du jeu uno
     */
    .controller('GameController', ['$rootScope', '$scope', '$http', '$stateParams', '$timeout', 'Game', function ($rootScope, $scope, $http, $stateParams, $timeout, Game) {
        var timeoutStateGame;
        $scope.currentPlayer = '';

        // Utilisation du service Game pour récupérer la main du joueur connecté
        Game.getUserHand($stateParams.name)
            .then(function (response) {
                $scope.cartes = response.data.cartes;
            });

        // Utilisation du service Game pour récupérer le statut du jeu
        Game.getGame($stateParams.name)
            .then(function (response) {
                $scope.game = response.data;
                $scope.requestStateGame();
            }, function () {
                $scope.requestStateGame();
            });

        // Fonction qui contient un timer permet de requêter toutes les 2 secondes
        // sur l'état du jeu afin de le mettre à jour
        $scope.requestStateGame = function () {
            // Timer toutes les 2 secondes
            timeoutStateGame = $timeout(function () {
                // Utilisation du service Game pour récupérer l'état du jeu
                Game.getGame($stateParams.name)
                    .then(function (response) {
                        $scope.game = response.data;

                        // Utilisation du service Game pour récupérer le joueur devant jouer
                        Game.getCurrentPlayer($stateParams.name)
                            .then(function (response) {
                                // Si le joueur n'est pas le même qu'à la précédente requête
                                // alors une modal apparait avec le nom du joueur devant jouer
                                if ($scope.currentPlayer !== response.data.playerName) {
                                    $scope.currentPlayer = response.data.playerName;
                                    jQuery('.myModalCurrentPlayer').modal();
                                    $timeout(function () {
                                        jQuery('.myModalCurrentPlayer').modal('hide');
                                    }, 2000);
                                }
                            });

                        // Utilisation du service Game pour récupérer la main du joueur connecté
                        Game.getUserHand($stateParams.name)
                            .then(function (response) {
                                $scope.cartes = response.data.cartes;
                            });

                        // La fonction s'appelle elle même
                        $scope.requestStateGame();
                    }, function () {
                        // La fonction s'appelle elle même si la requête précédente a échoué
                        $scope.requestStateGame();
                    });
            }, 500);
        };

        // Fonction qui permet au joueur connecté de piocher une carte
        $scope.piocherCarte = function () {
            // Utilisation du service Game pour piocher une carte
            Game.drawCard($stateParams.name)
                .then(function () {
                    // Utilisation du service Game pour récupérer la main du joueur connecté
                    Game.getUserHand($stateParams.name)
                        .then(function (response) {
                            $scope.cartes = response.data.cartes;
                        });
                });
        };

        // Fonction qui permet au joueur connecté de jouer une carte
        $scope.jouerCarte = function (carte) {
            // Utilisation du service Game pour jouer une carte
            Game.playCard($stateParams.name, carte)
                .then(function () {
                    // Utilisation du service Game pour récupérer la main du joueur connecté
                    Game.getUserHand($stateParams.name)
                        .then(function (response) {
                            $scope.cartes = response.data.cartes;
                        });

                    // Utilisation du service Game pour récupérer l'état du jeu
                    Game.getGame($stateParams.name)
                        .then(function (response) {
                            $scope.game = response.data;
                        });
                });
        };

        // Évènement qui permet d'arrêter la timer quand on quitte le contrôleur actuel
        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutStateGame);
        });
    }]);
