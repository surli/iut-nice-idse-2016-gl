'use strict';

angular.module('unoApp')
    .controller('RoomController', ['$rootScope', '$scope', '$state', '$stateParams', '$http', '$timeout', 'Game', function ($rootScope, $scope, $state, $stateParams, $http, $timeout, Game) {
        $scope.gameName = $stateParams.name;
        var timeoutStateGame;
        Game.getGame($scope.gameName)
            .then(function (response) {
                $scope.game = response.data;
                $scope.requestStateGame();
            }, function () {
                $scope.requestStateGame();
            });

        $scope.requestStateGame = function () {
            timeoutStateGame = $timeout(function () {
                Game.getGame($scope.gameName)
                    .then(function (response) {
                        $scope.game = response.data;
                        if ($scope.game.state) {
                            $state.go('app.game', {name: $scope.gameName});
                        } else {
                            $scope.requestStateGame();
                        }
                    }, function () {
                        $scope.requestStateGame();
                    });
            }, 5000);
        };

        $scope.startGameNow = function () {
            $http.put('/rest/game/' + $scope.gameName + '/command', {
                    playerName: $scope.user.name
                }, {
                    headers: {
                        token: $scope.user.token
                    }
                })
                .then(function (response) {
                    switch (response.status) {
                        case 200 :
                            if (response.data.status) {
                                $state.go('app.game', {name: $scope.gameName});
                            } else {
                                console.error(response);
                            }
                            break;
                        default:
                            console.error(response);
                    }
                }, function (error) {
                    console.error(error);
                });
        };

        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutStateGame);
        });
    }]);
