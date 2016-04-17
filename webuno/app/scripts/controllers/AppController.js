'use strict';

angular.module('unoApp')
    /**
     * Contrôleur global AppController de la route /app
     * Gère l'ensemble de l'application une fois un utilisateur connecté
     */
    .controller('AppController', ['$rootScope', '$scope', '$state', '$translate', '$timeout', '$q', 'Auth', 'Game', function ($rootScope, $scope, $state, $translate, $timeout, $q, Auth, Game) {
        // Si l'utilisateur n'est pas connecté alors il est redirigé vers la page de connexion
        // sinon $scope.user contient les données utlisateur
        if (!Auth.isConnected()) {
            $state.go('login');
        } else {
            $scope.user = Auth.getUser();
        }

        // Fonction qui permet de détruire l'utilisateur et de le rediriger vers la page de connexion
        $scope.goLogout = function () {
            if ($state.current.name === 'app.room' || $state.current.name === 'app.game') {
                $rootScope.logout = true;
                Game.quitRoom($rootScope.gameName, function () {
                    Auth.decoUser(function () {
                        Auth.destroyUser();
                        $state.go('login');
                    });
                });
            } else {
                Auth.decoUser(function () {
                    Auth.destroyUser();
                    $state.go('login');
                });
            }
        };

        $rootScope.goTo = function(url, timeout, callback) {
            if (timeout) {
                $timeout(function() {
                    if (angular.isFunction(callback)) {
                        callback();
                    }
                }, timeout)
                    .then(function() {
                        $state.go(url);
                });
            } else {
                if (angular.isFunction(callback)) {
                    callback();
                }

                $state.go(url);
            }
        };

        google.charts.load('current', {'packages': ['corechart']});
    }]);