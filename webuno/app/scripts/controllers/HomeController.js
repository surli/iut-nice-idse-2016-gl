'use strict';

angular.module('unoApp')
    .controller('HomeController', ['$scope', '$timeout', '$http', '$state', 'Game', function ($scope, $timeout, $http, $state, Game) {
        var timeoutListGames;

        Game.getAllGames()
            .then(function (response) {
                $scope.games = response.data.games;
                $scope.requestListGames();
            }, function () {
                $scope.requestListGames();
            });

        $scope.requestListGames = function () {
            timeoutListGames = $timeout(function () {
                Game.getAllGames()
                    .then(function (response) {
                        $scope.games = response.data.games;
                        $scope.requestListGames();
                    }, function () {
                        $scope.requestListGames();
                    });
            }, 2000);
        };

        $scope.joinGame = function (gameName) {
            Game.joinGame(gameName)
                .then(function (response) {
                    switch (response.status) {
                        case 200 :
                            if (response.data.status) {
                                $state.go('app.room', {name: gameName});
                            } else {
                                $scope.error = response;
                            }
                            break;
                        default :
                            $scope.error = response;
                    }
                });
        };

        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutListGames);
        });
    }]);
