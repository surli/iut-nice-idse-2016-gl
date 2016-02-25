'use strict';

angular.module('unoApp')
    .controller('StartController', ['$rootScope', '$scope', '$state', '$http', 'Game', function ($rootScope, $scope, $state, $http, Game) {
        $scope.nbPlayers = '2';

        $scope.goGame = function () {
            if ($scope.game && $scope.game.length > 3 && $scope.user.name) {
                Game.createGame($scope.game, $scope.nbPlayers)
                    .then(function(data) {
                        switch (data.status) {
                            case 200 :
                                $state.go('app.room', { name: $scope.game });
                                break;
                            default:
                                $scope.error = data.error;
                        }
                    }, function(error) {
                        $scope.error = 'An error occured : ' + error;
                    });
            } else {
                $scope.error = '3 characters minimum required to name the game';
            }
        };
    }]);
