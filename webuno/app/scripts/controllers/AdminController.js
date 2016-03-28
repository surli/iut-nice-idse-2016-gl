'use strict';

angular.module('unoApp')
    /**
     * Contrôleur global AppController de la route /app
     * Gère l'ensemble de l'application une fois un utilisateur connecté
     */
    .controller('AdminController', ['$rootScope', '$scope', '$state', 'Auth', 'Game', function ($rootScope, $scope, $state, Auth, Game) {
        if (!Auth.isAdmin()) {
            $state.go('login');
        }

        function initAdmin() {
            Game.getAllGamesAdmin(function (data) {
                $scope.games = data.games;
                console.log($scope.games);

                google.charts.load('current', {'packages': ['corechart']});
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

                    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
                    chart.draw(google.visualization.arrayToDataTable(games), {});
                });
            });
        }

        initAdmin();

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
    }]);