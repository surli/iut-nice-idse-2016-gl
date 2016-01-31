'use strict';

angular.module('unoApp')
    .controller("GameController", ["$rootScope", "$scope", "$http", function ($rootScope, $scope, $http) {
    // TODO : IN GAME
    $rootScope.inGame   = true;
    $rootScope.username = "George";
    $rootScope.points   = 0;

    //$http.get("localhost:8080/rest/jeu/start/"+$rootScope.username);

    $scope.cartes = [
        {famille: 'Carreau', nombre: 1},
        {famille: 'Pique', nombre: 2},
        {famille: 'Trefle', nombre: 3},
        {famille: 'Coeur', nombre: 4},
        {famille: 'Carreau', nombre: 5},
        {famille: 'Trefle', nombre: 6},
        {famille: 'Coeur', nombre: 7},
        {famille: 'Coeur', nombre: 8}
    ];

    $scope.fausse = {famille: 'Pique', nombre: 6};

    $scope.piocherCarte = function() {
        $scope.cartes.push($scope.cartes[$scope.cartes.length%8]);
    };

    $scope.jouerCarte = function(carte) {
        $scope.cartes.splice($scope.cartes.indexOf(carte) ,1);
        $scope.fausse = carte;
    };
}]);