'use strict';

angular.module('unoApp')
    .controller('GameController', ['$rootScope', '$scope', '$http', '$stateParams', 'ENV', function ($rootScope, $scope, $http, $stateParams, ENV) {
        $http.get(ENV.apiEndpoint + 'rest/game/' + $stateParams.name + '/' + $scope.user.name)
            .then(function (data) {
                $scope.cartes = data.data.cartes;
                console.log($scope.cartes);
            }, function (error) {
                $scope.error = "Une erreur est survenue : " + error.toString();
            });

        $http.get(ENV.apiEndpoint + 'rest/game/' + $stateParams.name)
            .then(function (response) {
                console.log(response.data);
                //$scope.fausse = {};
            }, function (error) {
                console.error(error);
            });

        $scope.piocherCarte = function () {
            $scope.cartes.push($scope.cartes[$scope.cartes.length % 8]);
        };

        $scope.jouerCarte = function (carte) {
            $scope.cartes.splice($scope.cartes.indexOf(carte), 1);
            $scope.fausse = carte;
        };
    }]);
