'use strict';

angular.module('unoApp')
    .controller('RegisterController', ['$rootScope', '$scope', '$state', 'Auth', function ($rootScope, $scope, $state, Auth) {
        if (Auth.isConnected()) {
            $state.go('app.home');
        }

        $scope.newUser  = {};
        $scope.error    = '';

        $scope.goRegister = function () {
            Auth.registerUser($scope.newUser)
                .then(function (response) {
                    if (response.data.error) {
                        $scope.error = response.data.error;
                    } else {
                        response.data.name = $scope.newUser.name;
                        Auth.connectUser(response.data);
                        $state.go('app.home');
                    }
                });
        };
    }]);