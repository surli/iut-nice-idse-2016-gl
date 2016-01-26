angular.module('unoApp')
    .controller("StartController", ["$rootScope", "$scope", "$location", function ($rootScope, $scope, $location) {
        // TODO : FOR START GAME
        $rootScope.goGame = function () {
            if ($scope.username) {
                $location.path('app/game');
            }
        };
    }]);