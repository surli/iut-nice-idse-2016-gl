angular.module('unoApp')
    .controller("RegisterController", ["$rootScope", "$scope", "$state", function ($rootScope, $scope, $state) {
        // TODO : FOR REGISTER
        $scope.goRegister = function () {
            // Jeremy faudra que on voit ensemble pour l'envoi vers REST
            $state.go('app.start');
        };
    }]);