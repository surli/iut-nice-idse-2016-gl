'use strict';

angular.module('unoApp')
    /**
     * Contrôleur global AppController de la route /app
     * Gère l'ensemble de l'application une fois un utilisateur connecté
     */
    .controller('AdminController', ['$rootScope', '$scope', '$state', '$timeout', 'Auth', 'Game', 'Users', function ($rootScope, $scope, $state, $timeout, Auth, Game, Users) {
        if (!Auth.isAdmin()) {
            $state.go('login');
        }

        $rootScope.callbackHome = false;

        // Fonction qui met à jour les listing de l'admin
        function initAdmin() {
            // Utilisation du service Game pour recupérer la liste des parties
            Game.getAllGamesAdmin(function (data) {
                $scope.games = data.games;

                var games = [0, 0, 0];

                angular.forEach($scope.games, function (game) {
                    if (game.state) {
                        games[2]++;
                    } else {
                        if (game.numberplayer === game.maxplayer) {
                            games[1]++;
                        } else {
                            games[0]++;
                        }
                    }
                });

                $scope.statsChart = {};
                $scope.statsChart.type = 'PieChart';
                $scope.statsChart.data = {
                    'cols': [
                        {id: 't', label: 'Statut', type: 'string'},
                        {id: 's', label: 'Nombre de parties', type: 'number'}
                    ], 'rows': [
                        {
                            c: [
                                {v: 'Complète'},
                                {v: games[1]}
                            ]
                        },
                        {
                            c: [
                                {v: 'Demarrée'},
                                {v: games[2]}
                            ]
                        },
                        {
                            c: [
                                {v: 'Terminée'},
                                {v: 0}
                            ]
                        },
                        {
                            c: [
                                {v: 'En attente de joueurs'},
                                {v: games[0]}
                            ]
                        }
                    ]
                };

                $scope.statsChart.options = {};
            });

            // Utilisation du service Users pour récupérer la liste de tous les users
            Users.getAllUsers(function (data) {
                $scope.allusers = data.users;

                $scope.allusers.forEach(function(user) {
                    user.role = user.role.toString();
                });
            });
        }

        // Utilisation de la mise a jour des listing de l'admin
        initAdmin();

        // Fonction qui permet de voir les détails d'une partie (liste des joueurs y participant)
        $scope.goVisualisation = function (gameName) {
            // Utilisation du service Game pour afficher les détails d'une partie
            Game.getGameAdmin(gameName, function (data) {
                $scope.gameVisu = data;
                jQuery('.modalVisuGame').modal();
            });
        };

        // Fonction qui permet de supprimer la partie choisi
        $scope.goDelete = function (gameName) {
            // Utilisation du service Game pour supprimer une partie
            Game.deleteGame(gameName, function () {
                // Utilisation de la mise a jour des listing de l'admin
                initAdmin();
            });
        };

        // Fonction qui permet de mettre à jour le role du joueur choisi
        $scope.goUpdateRole = function (userName, userRole) {
            // Utilisation du service Users pour changer le role d'un utilisateur
            Users.updateRoleUser(userName, userRole, function () {
                // Utilisation de la mise a jour des listing de l'admin
                initAdmin();
            });
        };

        // Fonctionne qui permet de bannir ou débannir le joueur choisi
        $scope.goUpdateBan = function (userName, userBan) {
            // Utilisation du service Users pour bannir ou débannir un utilisateur
            Users.updateBanUser(userName, userBan, function () {
                // Utilisation de la mise a jour des listing de l'admin
                initAdmin();
            });
        };
    }]);
