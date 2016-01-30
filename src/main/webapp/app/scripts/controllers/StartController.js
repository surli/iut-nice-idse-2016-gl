angular.module('unoApp')
    .controller("StartController", ["$rootScope", "$scope", "$state", function ($rootScope, $scope, $state) {
        // TODO : FOR START GAME
        $scope.game = {
            name: "",
            nbGamers: 1
        };

        $scope.goGame = function () {
            if ($scope.game.name) {
                $state.go('app.game');
            }
        };
    }]);