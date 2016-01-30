angular.module('unoApp')
    .controller("LoginController", ["$rootScope", "$scope", "$state", function ($rootScope, $scope, $state) {
        // TODO : FOR CONNECT
        $scope.goLogin = function () {
            if ($scope.email && $scope.pass) {
                $state.go('app.start');
            }
        };

        $scope.goLoginGuess = function () {
            $state.go('app.start');
        };
    }]);