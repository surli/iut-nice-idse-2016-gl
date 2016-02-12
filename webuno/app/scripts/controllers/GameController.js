'use strict';

angular.module('unoApp')
    .controller('GameController', ['$rootScope', '$scope', '$http', '$stateParams', '$timeout', function ($rootScope, $scope, $http, $stateParams, $timeout) {
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
                console.log("Game : ", response.data);
                $scope.fausse = response.data.stack;
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
                        console.log("Game : ", response.data);
                        $scope.fausse = response.data.stack;
                        $scope.requestStateGame();

                        $http.get('/rest/game/' + $stateParams.name + '/' + $scope.user.name)
                            .then(function (response) {
                                console.log("Cartes : ", response.data.cartes);
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
            $scope.cartes.push($scope.cartes[$scope.cartes.length % 8]);
        };

        $scope.jouerCarte = function (carte) {
            $scope.cartes.splice($scope.cartes.indexOf(carte), 1);
            $scope.fausse = carte;
        };

        $scope.$on('$destroy', function () {
            $timeout.cancel();
        });
    }]);
