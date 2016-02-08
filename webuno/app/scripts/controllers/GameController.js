'use strict';

angular.module('unoApp')
    .controller('GameController', ['$rootScope', '$scope', '$http', '$stateParams', function ($rootScope, $scope, $http, $stateParams) {
        $http.get('/rest/game/' + $stateParams.name + '/' + $scope.user.name)
            .then(function (data) {
                $scope.cartes = data.data.cartes;
                console.log($scope.cartes);
            }, function (error) {
                $scope.error = "Une erreur est survenue : " + error.toString();
            });

        //$scope.fausse = {};

        $scope.piocherCarte = function () {
            $scope.cartes.push($scope.cartes[$scope.cartes.length % 8]);
        };

        $scope.jouerCarte = function (carte) {
            $scope.cartes.splice($scope.cartes.indexOf(carte), 1);
            $scope.fausse = carte;
        };
    }]);
