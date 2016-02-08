'use strict';

angular.module('unoApp')
    .controller('RoomController', ['$rootScope', '$scope', '$state', '$stateParams', '$http', '$timeout', function ($rootScope, $scope, $state, $stateParams, $http, $timeout) {
        $scope.gameName = $stateParams.name;

        $http.get('/rest/game/' + $scope.gameName)
            .then(function (response) {
                $scope.game = response.data;
                console.log("First GameState : ", $scope.game);
                $scope.requestStateGame();
            }, function (error) {
                console.log(error);
                $scope.requestStateGame();
            });

        $scope.requestStateGame = function () {
            $timeout(function () {
                $http.get('/rest/game/' + $scope.gameName)
                    .then(function (response) {
                        $scope.game = response.data;
                        console.log("GameState : ", $scope.game);
                        $scope.requestStateGame();
                    }, function (error) {
                        console.log(error);
                        $scope.requestStateGame();
                    });
            }, 5000);
        };

        $scope.startGameNow = function () {
            $http.put('/rest/game/' + $scope.gameName + '/command', {
                    playerName: $scope.user.name
                })
                .then(function (data) {
                    console.log(data);
                }, function (error) {

                });
        }
    }]);
