'use strict';

angular.module('unoApp')
    .controller('HomeController', ['$scope', '$timeout', '$http', '$state', 'Games', function ($scope, $timeout, $http, $state, Games) {
        $scope.games = Games.data.games;

        $scope.requestListGames = function () {
            $timeout(function () {
                $http.get('/rest/game/')
                    .then(function (data) {
                        $scope.games = data.data.games;
                        console.log("ListGames : ", $scope.games);
                        $scope.requestListGames();
                    }, function (error) {
                        console.log(error);
                        $scope.requestListGames();
                    });
            }, 5000);
        };

        $scope.requestListGames();

        $scope.joinGame = function (gameName) {
            $http.put('/rest/game/' + gameName, {
                    playerName: $scope.user.name
                })
                .then(function (response) {
                    console.log("Ajout joueur: ", response);
                    switch (response.status) {
                        case 200 :
                            if (response.data.status) {
                                $state.go('app.room', {name: gameName});
                            } else {
                                console.log("Error : ", response);
                            }
                            break;
                        default :
                            console.log("Error : ", response);
                    }
                }, function (error) {
                    console.log(error);
                });
        };
    }]);