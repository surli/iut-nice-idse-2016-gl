'use strict';

angular.module('unoApp')
  .controller('HomeController', ['$scope', 'Games', function ($scope, Games) {
    $scope.games = Games.data.games;
    console.log($scope.games);
  }]);
