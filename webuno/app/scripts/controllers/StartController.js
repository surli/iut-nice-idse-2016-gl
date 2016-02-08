'use strict';

angular.module('unoApp')
    .controller('StartController', ['$rootScope', '$scope', '$state', '$http', function ($rootScope, $scope, $state, $http) {
        $scope.goGame = function () {
            if ($scope.game && $scope.game.length > 3 && $scope.user.name) {
              console.log($scope.game, $scope.user.name);
                $http.post('/rest/game', {
                        _token: 'hbj7BB7Y6B87T282B87T27N90A098',
                        game:   $scope.game,
                        player: $scope.user.name
                    })
                    .then(function(data) {
                        switch (data.status) {
                            case 200 :
                                $state.go('app.room', { name: $scope.game });
                                break;
                            case 405 :
                                $scope.error = "Tu participes déjà à cette partie !";
                                break;
                            case 500 :
                                $scope.error = "Partie déjà créée !";
                                break;
                            default:
                                $scope.error = "Une erreur est survenue...";
                        }
                    }, function(error) {
                        $scope.error = "Une erreur est survenue : " + error;
                    });
            } else {
                $scope.error = "3 caractères minimum est requis pour le nom de la partie";
            }
        };
    }]);
