'use strict';

angular.module('unoApp')
    .controller('RegisterController', ['$rootScope', '$scope', '$state', 'Auth', function ($rootScope, $scope, $state, Auth) {
        if (Auth.isConnected()) {
            $state.go('app.home');
        }

        $scope.goRegister = function () {
            // TODO ajouter fonction Auth.register($scope.email,$scope.name,$scope.password)
            window.alert('Register not available !');
        };
    }]);