'use strict';

angular.module('unoApp')
    /**
     * Contrôleur GameController de la route /app/game
     * Gère le jeu/la partie du jeu uno
     */
    .controller('GameController', ['$rootScope', '$scope', '$http', '$state', '$stateParams', '$timeout', 'Game', function ($rootScope, $scope, $http, $state, $stateParams, $timeout, Game) {
        // On récupère id de la room passé en paramètre dans l'url
        $rootScope.gameName = $stateParams.name;
        $scope.currentPlayer = '';
        var timeoutStateGame;

        // Utilisation du service Game pour récupérer la main du joueur connecté
        Game.getUserHand($rootScope.gameName, function (data) {
            $scope.cartes = data.cartes;
        });

        // Utilisation du service Game pour récupérer le statut du jeu
        Game.getGame($rootScope.gameName, function (data) {
            $scope.game = data;
            $scope.requestStateGame();
        }, function () {
            $timeout.cancel(timeoutStateGame);
            $state.go('login');
        });

        // Fonction qui contient un timer permet de requêter toutes les 2 secondes
        // sur l'état du jeu afin de le mettre à jour
        $scope.requestStateGame = function () {
            // Timer toutes les 2 secondes
            timeoutStateGame = $timeout(function () {
                // Utilisation du service Game pour récupérer l'état du jeu
                Game.getGame($rootScope.gameName, function (data) {
                    $scope.game = data;
                    //console.log($scope.game);

                    // Utilisation du service Game pour récupérer le joueur devant jouer
                    Game.getCurrentPlayer($rootScope.gameName, function (data) {
                        // Si le joueur n'est pas le même qu'à la précédente requête
                        // alors une modal apparait avec le nom du joueur devant jouer
                        if ($scope.currentPlayer !== data.playerName) {
                            $scope.currentPlayer = data.playerName;
                            jQuery('.myModalCurrentPlayer').modal();
                            $timeout(function () {
                                jQuery('.myModalCurrentPlayer').modal('hide');
                            }, 2000);
                        }
                        testIfCanPlay();
                    });

                    // Utilisation du service Game pour récupérer la main du joueur connecté
                    Game.getUserHand($rootScope.gameName, function (data) {
                        $scope.cartes = data.cartes;
                        $scope.playable = data.playable;
                        testIfCanPlay();
                    });

                    if ($scope.game.gameEnd) {
                        $timeout.cancel(timeoutStateGame);
                    } else {
                        // La fonction s'appelle elle même
                        $scope.requestStateGame();
                    }
                }, function () {
                    $timeout.cancel(timeoutStateGame);
                    $state.go('login');
                });
            });
        };

        // Fonction qui permet au joueur connecté de piocher une carte
        $scope.piocherCarte = function () {
            // Utilisation du service Game pour piocher une carte
            Game.drawCard($rootScope.gameName, function () {
                // Utilisation du service Game pour récupérer la main du joueur connecté
                Game.getUserHand($rootScope.gameName, function (data) {
                    $scope.cartes = data.cartes;
                    $scope.playable = data.playable;
                    testIfCanPlay();
                });
            });
        };

        // Fonction qui permet au joueur connecté de jouer une carte
        $scope.jouerCarte = function (carte) {
            if (carte.family === 'Black') {
                jQuery('.myModalColorChoose').modal();

                $scope.chooseColor = function (color) {
                    carte.setcolor = color;
                    // Utilisation du service Game pour jouer une carte
                    Game.playCard($rootScope.gameName, carte, function () {
                        jQuery('.myModalColorChoose').modal('hide');

                        // Utilisation du service Game pour récupérer la main du joueur connecté
                        Game.getUserHand($rootScope.gameName, function (data) {
                            $scope.cartes = data.cartes;
                            $scope.playable = data.playable;
                        });

                        // Utilisation du service Game pour récupérer l'état du jeu
                        Game.getGame($rootScope.gameName, function (data) {
                            $scope.game = data;
                            testIfCanPlay();
                        });
                    });
                };
            } else {
                // Utilisation du service Game pour jouer une carte
                Game.playCard($rootScope.gameName, carte, function () {
                        // Utilisation du service Game pour récupérer la main du joueur connecté
                        Game.getUserHand($rootScope.gameName, function (data) {
                            $scope.cartes = data.cartes;
                            $scope.playable = data.playable;
                            testIfCanPlay();
                        });

                        // Utilisation du service Game pour récupérer l'état du jeu
                        Game.getGame($rootScope.gameName, function (data) {
                            $scope.game = data;
                        });
                    });
            }
        };

        function testIfCanPlay() {
            //console.log($scope.playable);
            if ($scope.user.name === $scope.currentPlayer) {
                if ($scope.playable.length > 0) {
                    $scope.playerCanPlay = true;
                    $scope.playable.forEach(function (val) {
                        $scope.cartes.forEach(function (carte) {
                            if (val.family === carte.family && val.number === carte.number) {
                                carte.playable = true;
                            }
                        });
                    });
                } else {
                    $scope.playerCanPlay = false;
                }
            } else {
                $scope.playerCanPlay = false;
            }
        }

        $rootScope.callbackHome = function(url) {
            $timeout.cancel(timeoutStateGame);
            jQuery('.myModalColorChoose').modal('hide');
            Game.quitRoom($rootScope.gameName, function() {
                $state.go(url);
            }, function () {
                $state.go(url);
            });
        };
    }]);
