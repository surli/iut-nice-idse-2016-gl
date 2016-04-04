'use strict';

angular.module('unoApp')
    /**
     * Contrôleur global AppController de la route /app
     * Gère l'ensemble de l'application une fois un utilisateur connecté
     */
    .controller('AdminController', ['$rootScope', '$scope', '$state', '$timeout', 'Auth', 'Game','Users', function ($rootScope, $scope, $state, $timeout, Auth, Game, Users) {
        if (!Auth.isAdmin()) {
            $state.go('login');
        }

        function initAdmin() {
            Game.getAllGamesAdmin(function (data) {
                $scope.games = data.games;

                $timeout(function() {
                    google.charts.setOnLoadCallback(function () {
                        var games = [
                            ['Statut', 'Nombre de parties'],
                            ['En attente de joueurs', 0],
                            ['Complète', 0],
                            ['Demarrée', 0],
                            ['Terminée', 0]
                        ];

                        angular.forEach($scope.games, function (game) {
                            if (game.state) {
                                games[3][1]++;
                            } else {
                                if (game.numberplayer === game.maxplayer) {
                                    games[2][1]++;
                                } else {
                                    games[1][1]++;
                                }
                            }
                        });

                        var chartGames = new google.visualization.PieChart(document.getElementById('piechartgames'));
                        chartGames.draw(google.visualization.arrayToDataTable(games), {});
                    });
                }, 1000);
            });
        }

        //function waitForInitChart() {
        //    if (document.getElementById('piechartgames')) {
                initAdmin();
            //} else {
            //    waitForInitChart();
            //}
        //}

        //waitForInitChart();

        $scope.goVisualisation = function (gameName) {
            Game.getGameAdmin(gameName, function (data) {
                console.log(data);
                $scope.gameVisu = data;
                jQuery('.modalVisuGame').modal();
            });
        };

        $scope.goDelete = function (gameName) {
            Game.deleteGame(gameName, function () {
                initAdmin();
            });
        };

      // Utilisation du service Users pour récupérer la liste de tous les users
      Users.getAllUsers(function (data) {
        // SUCCESS
      }, function () {
        var data = {
          users: [
            {
              id: '1',
              pseudo: 'toto',
              email: 'toto@gmail.com',
              role: '1',
              banned: '0'

            },
            {
              id: '2',
              pseudo: 'tata',
              email: 'tata@gmail.com',
              role: '3',
              banned: '1'
            }
          ]
        };
        $scope.allusers = data.users; // fictif en attendant la vrai route
        console.log($scope.allusers);
      });

    }]);
