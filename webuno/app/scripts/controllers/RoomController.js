'use strict';

angular.module('unoApp')
    .controller('RoomController', ['$rootScope', '$scope', '$state', '$stateParams', '$http', '$timeout', function ($rootScope, $scope, $state, $stateParams, $http, $timeout) {
        $scope.gameName = $stateParams.name;
        var timeoutStateGame;

        $http.get('/rest/game/' + $scope.gameName)
            .then(function (response) {
                $scope.game = response.data;
                $scope.requestStateGame();
            }, function (error) {
                console.error(error);
                $scope.requestStateGame();
            });

        $scope.requestStateGame = function () {
            timeoutStateGame = $timeout(function () {
                $http.get('/rest/game/' + $scope.gameName)
                    .then(function (response) {
                        $scope.game = response.data;
                        if ($scope.game.state) {
                            $state.go('app.game', { name: $scope.gameName });
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
            $http.put('/rest/game/' + $scope.gameName + '/command', {
                    playerName: $scope.user.name
                })
                .then(function (response) {
                    switch(response.status) {
                        case 200 :
                            if (response.data.status) {
                                $state.go('app.game', { name: $scope.gameName });
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

        $scope.$on('$destroy', function(){
            $timeout.cancel(timeoutStateGame);
        });
    }]);
