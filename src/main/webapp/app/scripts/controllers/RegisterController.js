/**
 * Created by Tev on 28/01/2016.
 */
angular.module('unoApp')
    .controller("LoginController", ["$rootScope", "$scope", "$location", function ($rootScope, $scope, $location) {
        // TODO : FOR CONNECT
        $scope.goAuth = function () {
            if ($scope.email && $scope.pass) {
                $location.path('app/start');
            }
        };
    }]);