'use strict';

angular.module('unoApp')
    .controller('GameController', ['$rootScope', '$scope', '$http', '$stateParams', '$timeout', 'Game', function ($rootScope, $scope, $http, $stateParams, $timeout, Game) {
        var timeoutStateGame;
        $scope.currentPlayer = '';

        Game.getUserHand($stateParams.name)
            .then(function (response) {
                $scope.cartes = response.data.cartes;
            });

        Game.getGame($stateParams.name)
            .then(function (response) {
                $scope.game = response.data;
                $scope.requestStateGame();
            }, function () {
                $scope.requestStateGame();
            });

        $scope.requestStateGame = function () {
            timeoutStateGame = $timeout(function () {
                Game.getGame($stateParams.name)
                    .then(function (response) {
                        $scope.game = response.data;

                        Game.getCurrentPlayer($stateParams.name)
                            .then(function (response) {
                                if ($scope.currentPlayer !== response.data.playerName) {
                                    $scope.currentPlayer = response.data.playerName;
                                    jQuery('.myModalCurrentPlayer').modal();
                                    $timeout(function () {
                                        jQuery('.myModalCurrentPlayer').modal('hide');
                                    }, 2000);
                                }
                            });

                        Game.getUserHand($stateParams.name)
                            .then(function (response) {
                                $scope.cartes = response.data.cartes;
                            });

                        $scope.requestStateGame();
                    }, function () {
                        $scope.requestStateGame();
                    });
            }, 500);
        };

        $scope.piocherCarte = function () {
            Game.drawCard($stateParams.name)
                .then(function () {
                    Game.getUserHand($stateParams.name)
                        .then(function (response) {
                            $scope.cartes = response.data.cartes;
                        });
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
                        });
                });
        };

        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutStateGame);
        });
    }]);
