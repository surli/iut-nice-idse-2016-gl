'use strict';

angular.module('unoApp')
    /**
     * Contrôleur HomeController de la route /app/home
     * Gère l'affichage des parties en cours
     */
    .controller('HomeController', ['$scope', '$timeout', '$http', '$state', 'Game', function ($scope, $timeout, $http, $state, Game) {
        var timeoutListGames;

        // Utilisation du service Game pour récupérer la liste de toutes les parties avec leur état
        Game.getAllGames()
            .then(function (response) {
                $scope.games = response.data.games;
                // On appelle la fonctio requestListGames() pour lancer le timer (l'actualisation des parties)
                $scope.requestListGames();
            }, function () {
                $scope.requestListGames();
            });

        // Fonction qui permet de récupérer la liste des parties toutes les 2 secondes
        $scope.requestListGames = function () {
            // Timer de 2 secondes
            timeoutListGames = $timeout(function () {
                // Utilisation du service Game qui permet de récupérer la liste des toutes les parties avec leur statut
                Game.getAllGames()
                    .then(function (response) {
                        $scope.games = response.data.games;
                        // La fonction s'appelle elle même
                        $scope.requestListGames();
                    }, function () {
                        // La fonction s'appelle elle même si la précédente requête a échoué
                        $scope.requestListGames();
                    });
            }, 2000);
        };

        // Fonction qui permet de rejoindre une partie
        $scope.joinGame = function (gameName) {
            // Utilisation du service Game pour rejoindre une partie
            Game.joinGame(gameName)
                .then(function (response) {
                    // Si la réponse est en statut 200 alors on entre dans la room
                    // sinon on affiche l'erreur
                    switch (response.status) {
                        case 200 :
                            if (response.data.status) {
                                $state.go('app.room', {name: gameName});
                            } else {
                                $scope.error = response;
                            }
                            break;
                        default :
                            $scope.error = response;
                    }
                });
        };

        // Évènement qui permet d'arrêter la timer quand on quitte le contrôleur actuel
        $scope.$on('$destroy', function () {
            $timeout.cancel(timeoutListGames);
        });

      /**
       * MES PARTIES
       */
       //Utilisation du service My Game pour récupérer la liste de toutes mes parties
        Game.getMyGames()
          .then(function (response) {
            var data = { games : [
                                {
                                  gamename: 'Partie test 1',
                                  maxplayer: 2,
                                  dategame: '2016/03/08 12:10' },
                                {
                                  gamename: 'Partie test 2',
                                  maxplayer: 3,
                                  dategame: '2016/03/08 11:10'}
                                ]
                        } ;
            $scope.mygames = data.games; // fictif en attendant la vrai route
            console.log($scope.mygames);
            // On appelle la fonctio requestMYListGames() pour lancer le timer (l'actualisation des parties)
          });

    }]);
