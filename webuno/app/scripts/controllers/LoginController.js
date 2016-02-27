'use strict';

angular.module('unoApp')
    .controller('LoginController', ['$rootScope', '$scope', '$state', 'Auth', function ($rootScope, $scope, $state, Auth) {
        if (Auth.isConnected()) {
            $state.go('app.home');
        }

        $scope.newUser = {};
        $scope.error = '';

        $scope.goLogin = function () {
            Auth.setUser($scope.newUser)
                .then(function (response) {
                    console.log(response);
                    if (response.data.error) {
                        $scope.error = response.data.error;
                    } else {
                        response.data.name = response.data.playerName;
                        Auth.connectUser(response.data);
                        $state.go('app.home');
                    }
                });
        };

        $scope.goLoginGuess = function () {
            var name = 'Anonymous' + Math.floor((Math.random() * (1000 - 1) + 1));
            Auth.setUserGuess(name)
                .then(function (response) {
                    response.data.name = name;
                    Auth.connectUser(response.data);
                    $state.go('app.home');
                });
        };
    }]);
