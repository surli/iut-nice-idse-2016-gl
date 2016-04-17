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
            // Utilisation du service Game pour recupérer la liste des parties
            Game.getAllGamesAdmin(function (data) {
                $scope.games = data.games;

                // Timeout la création du chart qui affiche les statistiques des parties
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

        initAdmin();

        $scope.goVisualisation = function (gameName) {
          Game.getGameAdmin(gameName, function (data) {

                $scope.gameVisu = data;
                jQuery('.modalVisuGame').modal();
            });
        };

        $scope.goDelete = function (gameName) {
            Game.deleteGame(gameName, function () {
                initAdmin();
            });
        };

        $scope.goUpdateRole = function (userName,userRole){
          console.log('nom : '+userName+' role : '+userRole);
          Users.updateRoleUser(userName,userRole,function (data){
         });
        };

      $scope.goUpdateBan = function (userName,userBan){
        var ban = (userBan == false)?0:1;
        console.log('nom : '+userName+' ban : '+ban);
        Users.updateBanUser(userName,userBan,function (data) {
        });
      };

      // Utilisation du service Users pour récupérer la liste de tous les users
      Users.getAllUsers(function (data) {
        // SUCCESS
        $scope.allusers = data.users;
        console.log('SUCCESS', $scope.allusers);
      }, function () {
        var data = { users: [] };
        $scope.allusers = data.users; // fictif en attendant la vrai route
        console.log('ERROR', $scope.allusers);
      });

    }]);
