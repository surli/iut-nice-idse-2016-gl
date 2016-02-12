'use strict';

angular.module('unoApp')
    .controller('GameController', ['$rootScope', '$scope', '$http', '$stateParams', '$timeout', 'Game', function ($rootScope, $scope, $http, $stateParams, $timeout, Game) {
        var timeoutStateGame;

        // TODO remplacer par Game.getUserHand(name,user.name)
        $http.get('/rest/game/' + $stateParams.name + '/' + $scope.user.name)
            .then(function (response) {
                $scope.cartes = response.data.cartes;
            }, function (error) {
                console.error('Une erreur est survenue : ' + error.toString());
            });

        // TODO remplacer par Game.getGame(name)
        $http.get('/rest/game/' + $stateParams.name)
            .then(function (response) {
                $scope.game = response.data;
                $scope.requestStateGame();
            }, function (error) {
                console.error('Une erreur est survenue : ' + error.toString());
                $scope.requestStateGame();
            });

        $scope.requestStateGame = function () {
            timeoutStateGame = $timeout(function () {
                //TODO remplacer par Game.getGame (attention il y a un traitement en plus que faire ?)
                $http.get('/rest/game/' + $stateParams.name)
                    .then(function (response) {
                        $scope.game = response.data;

                        $http.get('/rest/game/' + $stateParams.name + '/command')
                            .then(function (response) {
                                if (response.data.playerName === $scope.user.name) {
                                    console.log('à moi de jouer !');
                                }
                            }, function (error) {
                                console.error(error);
                            });

                        $scope.requestStateGame();

                        $http.get('/rest/game/' + $stateParams.name + '/' + $scope.user.name)
                            .then(function (response) {
                                $scope.cartes = response.data.cartes;
                            }, function (error) {
                                console.error('Une erreur est survenue : ' + error.toString());
                            });
                    }, function (error) {
                        console.error('Une erreur est survenue : ' + error.toString());
                        $scope.requestStateGame();
                    });
            }, 2000);
        };

        $scope.piocherCarte = function () {
            $http.post('/rest/game/' + $stateParams.name + '/' + $scope.user.name, {})
                .then(function () {
                    $http.get('/rest/game/' + $stateParams.name + '/' + $scope.user.name)
                        .then(function (response) {
                            $scope.cartes = response.data.cartes;
                        }, function (error) {
                            console.error('Une erreur est survenue : ' + error.toString());
                        });
                }, function (error) {
                    console.error(error);
                });
        };

        $scope.jouerCarte = function (carte) {
            $http.put('/rest/game/' + $stateParams.name + '/' + $scope.user.name, {
                    value: carte.number,
                    color: carte.familly
                })
                .then(function (response) {
                    console.log(response);
                    $http.get('/rest/game/' + $stateParams.name + '/' + $scope.user.name)
                        .then(function (response) {
                            $scope.cartes = response.data.cartes;
                        }, function (error) {
                            console.error('Une erreur est survenue : ' + error.toString());
                        });
                    $http.get('/rest/game/' + $stateParams.name)
                        .then(function (response) {
                            $scope.game = response.data;
                        }, function (error) {
                            console.error('Une erreur est survenue : ' + error.toString());
                        });
                }, function (error) {
                    console.error(error);
                });
        };

        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutStateGame);
        });
    }]);
