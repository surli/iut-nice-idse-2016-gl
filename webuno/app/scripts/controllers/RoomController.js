'use strict';

angular.module('unoApp')
    .controller('RoomController', ['$rootScope', '$scope', '$state', '$stateParams', '$http', function ($rootScope, $scope, $state, $stateParams, $http) {
        $scope.gameName = $stateParams.name;

        $http.get('/rest/game/' + $scope.gameName)
            .then(function (data) {
                $scope.game = data;
                console.log($scope.game);
            }, function (error) {
                console.log(error);
            });

    }]);
