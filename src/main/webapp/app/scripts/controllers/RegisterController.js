'use strict';

angular.module('unoApp')
    .controller("RegisterController", ["$rootScope", "$scope", "$state", "Auth", function ($rootScope, $scope, $state, Auth) {
        // TODO : REGISTER
        if (Auth.isConnected()) {
            $state.go("app.home");
        }

        $scope.goRegister = function () {
            window.alert("Register not available !");
        };
    }]);