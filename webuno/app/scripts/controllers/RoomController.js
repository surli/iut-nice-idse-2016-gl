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
                        $scope.requestStateGame();
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
                .then(function (data) {
                    console.log(data);
                }, function (error) {
                    console.error(error);
                });
        };

        $scope.$on('$destroy', function(){
            $timeout.cancel(timeoutStateGame);
        });
    }]);
