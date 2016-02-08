'use strict';

angular.module('unoApp')
    .controller('HomeController', ['$scope', '$timeout', '$http', 'Games', function ($scope, $timeout, $http, Games) {
        $scope.games = Games.data.games;
        console.log($scope.games);
        request();

        function request() {
            $timeout(function() {
                $http.get('/rest/game/')
                    .then(function (data) {
                        $scope.games = data;
                        console.log($scope.game);
                    }, function (error) {
                        console.log(error);
                    });
                request();
            }, 5000)
        }
    }]);