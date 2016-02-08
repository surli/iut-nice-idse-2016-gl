'use strict';

angular.module('unoApp')
    .controller('HomeController', ['$scope', '$timeout', '$http', '$state', 'Games', function ($scope, $timeout, $http, $state, Games) {
        $scope.games = Games.data.games;
        console.log($scope.games);

        requestListGames();

        function requestListGames() {
            $timeout(function () {
                $http.get('/rest/game/')
                    .then(function (data) {
                        $scope.games = data.data.games;
                        console.log($scope.games);
                    }, function (error) {
                        console.log(error);
                    });
                requestListGames();
            }, 5000);
        }

        $scope.joinGame = function (gameName) {
            $http.put('/rest/game/' + gameName, {
                    playerName: $scope.user.name
                })
                .then(function (data) {
                    console.log("Ajout joueur: ", data);
                    switch (data.status) {
                        case 200 :
                            $state.go('app.room', { name: gameName });
                            break;
                        default :
                            console.log("Error : ", data);
                    }
                }, function (error) {
                    console.log(error);
                });
        };
    }]);