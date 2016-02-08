'use strict';

angular.module('unoApp')
    .controller('RoomController', ['$rootScope', '$scope', '$state', '$stateParams', '$http', '$timeout', function ($rootScope, $scope, $state, $stateParams, $http, $timeout) {
        $scope.gameName = $stateParams.name;

        $http.get('/rest/game/' + $scope.gameName)
            .then(function (data) {
                $scope.game = data;
                console.log($scope.game);

                request();
            }, function (error) {
                console.log(error);
            });

        function request() {
            $timeout(function() {
                $http.get('/rest/game/' + $scope.gameName)
                    .then(function (data) {
                        $scope.game = data;
                        console.log($scope.game);
                    }, function (error) {
                        console.log(error);
                    });
                request();
            }, 5000)
        }
    }]);
