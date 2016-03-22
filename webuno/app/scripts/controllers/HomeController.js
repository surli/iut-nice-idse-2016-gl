'use strict';

angular.module('unoApp')
    /**
     * Contrôleur HomeController de la route /app/home
     * Gère l'affichage des parties en cours
     */
    .controller('HomeController', ['$scope', '$timeout', '$http', '$state', 'Game', function ($scope, $timeout, $http, $state, Game) {
        var timeoutListGames;

        // Fonction qui permet de récupérer la liste des parties toutes les 2 secondes
        $scope.requestListGames = function () {
            // Timer de 2 secondes
            timeoutListGames = $timeout(function () {
                // Utilisation du service Game qui permet de récupérer la liste des toutes les parties avec leur statut
                Game.getAllGames(function (data) {
                    $scope.games = data.games;
                    // La fonction s'appelle elle même
                    $scope.requestListGames();
                }, function () {
                    // La fonction s'appelle elle même
                    $scope.requestListGames();
                });
            }, 2000);
        };

        // On appelle la fonction requestListGames() pour lancer le timer (l'actualisation des parties)
        $scope.requestListGames();

        // Utilisation du service Game pour récupérer la liste de toutes les parties avec leur état
        Game.getAllGames(function (data) {
            $scope.games = data.games;
        });

        // Fonction qui permet de rejoindre une partie
        $scope.joinGame = function (gameName) {
            // Utilisation du service Game pour rejoindre une partie
            Game.joinGame(gameName, function () {
                $state.go('app.room', {name: gameName});
            });
        };

        // Évènement qui permet d'arrêter la timer quand on quitte le contrôleur actuel
        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutListGames);
        });

        // Utilisation du service Game pour récupérer la liste de toutes mes parties
        Game.getMyGames(function () {
            // SUCCESS
        }, function () {
            var data = {
                games: [
                    {
                        gamename: 'Partie test 1',
                        maxplayer: 2,
                        dategame: '2016/03/08 12:10'
                    },
                    {
                        gamename: 'Partie test 2',
                        maxplayer: 3,
                        dategame: '2016/03/08 11:10'
                    }
                ]
            };
            $scope.mygames = data.games; // fictif en attendant la vrai route
            console.log($scope.mygames);
        });

    }]);
