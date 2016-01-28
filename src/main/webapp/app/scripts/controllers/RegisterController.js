/**
 * Created by Tev on 28/01/2016.
 */
angular.module('unoApp')
    .controller("LoginController", ["$rootScope", "$scope", "$location", function ($rootScope, $scope, $location) {
        // TODO : FOR CONNECT
        $scope.goRegi = function () {
            if ($scope.prenom && $scope.email && $scope.pass) {
                // Jeremy faudra que on voit ensemble pour l'envoi vers REST
                //$location.path('app/start');
            }
        };
    }]);