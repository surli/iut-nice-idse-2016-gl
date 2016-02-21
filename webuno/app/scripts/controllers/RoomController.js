'use strict';

angular.module('unoApp')
    .controller('RoomController', ['$rootScope', '$scope', '$state', '$stateParams', '$http', '$timeout', function ($rootScope, $scope, $state, $stateParams, $http, $timeout) {
        $scope.gameName = $stateParams.name;
        var timeoutStateGame;
        // TODO remplacer par Game.getGame
        $http.get('/rest/game/' + $scope.gameName, {
                headers: {
                    token: $scope.user.token
                }
            })
            .then(function (response) {
                $scope.game = response.data;
                $scope.requestStateGame();
            }, function (error) {
                console.error(error);
                $scope.requestStateGame();
            });

        $scope.requestStateGame = function () {
            timeoutStateGame = $timeout(function () {
                //TODO remplacer par Game.getGame (attention il y a un traitement en plus que faire ?)
                $http.get('/rest/game/' + $scope.gameName, {
                        headers: {
                            token: $scope.user.token
                        }
                    })
                    .then(function (response) {
                        $scope.game = response.data;
                        if ($scope.game.state) {
                            $state.go('app.game', {name: $scope.gameName});
                        } else {
                            $scope.requestStateGame();
                        }
                    }, function (error) {
                        console.error(error);
                        $scope.requestStateGame();
                    });
            }, 5000);
        };

        $scope.startGameNow = function () {
            // TODO remplacer par ****
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
