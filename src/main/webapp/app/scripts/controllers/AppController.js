angular.module('unoApp')
    .controller("AppController", ["$rootScope", "$scope", "$state", "Auth", function ($rootScope, $scope, $state, Auth) {
        // TODO : APP
        if (!Auth.isConnected()) {
            $state.go("login");
        } else {
            $scope.user = Auth.getUser();
        }

        $scope.goLogout = function() {
            Auth.destroyUser();
            $state.go("login");
        }
    }]);