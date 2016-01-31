'use strict';

angular.module('unoApp')
    .controller("LoginController", ["$rootScope", "$scope", "$state", "Auth", function ($rootScope, $scope, $state, Auth) {
        // TODO : LOGIN
        if (Auth.isConnected()) {
            $state.go("app.home");
        }

        $scope.goLogin = function () {
            window.alert("Log in not available !");
        };

        $scope.goLoginGuess = function () {
            Auth.setUser({
                name: "Anonyme"
            });
            $state.go('app.home');
        };
    }]);