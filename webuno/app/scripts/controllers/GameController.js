'use strict';

angular.module('unoApp')
    .controller('GameController', ['$rootScope', '$scope', '$http', '$stateParams', '$timeout', 'Game', function ($rootScope, $scope, $http, $stateParams, $timeout, Game) {
        var timeoutStateGame;

        Game.getUserHand($stateParams.name)
            .then(function (response) {
                $scope.cartes = response.data.cartes;
            }, function (error) {
                console.error('Une erreur est survenue : ' + error.toString());
            });

        Game.getGame($stateParams.name)
            .then(function (response) {
                $scope.game = response.data;
                $scope.requestStateGame();
            }, function (error) {
                console.error('Une erreur est survenue : ' + error.toString());
                $scope.requestStateGame();
            });

        $scope.requestStateGame = function () {
            timeoutStateGame = $timeout(function () {
                Game.getGame($stateParams.name)
                    .then(function (response) {
                        $scope.game = response.data;

                        Game.getCurrentPlayer($stateParams.name)
                            .then(function (response) {
                                $scope.currentPlayer = response.data.playerName;
                                if (response.data.playerName === $scope.user.name) {
                                    console.log('à moi de jouer !');
                                }
                            });

                        Game.getUserHand($stateParams.name)
                            .then(function (response) {
                                $scope.cartes = response.data.cartes;
                            });

                        $scope.requestStateGame();
                    }, function (error) {
                        console.error('Une erreur est survenue : ' + error.toString());
                        $scope.requestStateGame();
                    });
            }, 2000);
        };

        $scope.piocherCarte = function () {
            Game.drawCard($stateParams.name)
                .then(function () {
                    Game.getUserHand($stateParams.name)
                        .then(function (response) {
                            $scope.cartes = response.data.cartes;
                        });
                }, function (error) {
                    console.error(error);
                });
        };

        $scope.jouerCarte = function (carte) {
            Game.playCard($stateParams.name, carte)
                .then(function () {
                    Game.getUserHand($stateParams.name)
                        .then(function (response) {
                            $scope.cartes = response.data.cartes;
                        });

                    Game.getGame($stateParams.name)
                        .then(function (response) {
                            $scope.game = response.data;
                        }, function (error) {
                            console.error('Une erreur est survenue : ' + error.toString());
                        });
                });
        };

        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutStateGame);
        });
    }]);
