'use strict';

angular.module('unoApp')
    .controller('LoginController', ['$rootScope', '$scope', '$state', 'Auth', function ($rootScope, $scope, $state, Auth) {
        if (Auth.isConnected()) {
            $state.go('app.home');
        }

        $scope.goLogin = function () {
            window.alert('Log in not available !');
        };

        $scope.goLoginGuess = function () {
            var name = 'Anonyme' + Math.floor((Math.random() * (1000 - 1) + 1));
            Auth.setUser({
                name: name
            }).then(function(data) {
                data.name = name;
                Auth.connectUser(data);
                $state.go('app.home');
            });
        };
    }]);