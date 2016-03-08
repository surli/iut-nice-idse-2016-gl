'use strict';

angular.module('unoApp')
    /**
     * Contrôleur global AppController de la route /app
     * Gère l'ensemble de l'application une fois un utilisateur connecté
     */
    .controller('AppController', ['$rootScope', '$scope', '$state', 'Auth', function ($rootScope, $scope, $state, Auth) {
        // Si l'utilisateur n'est pas connecté alors il est redirigé vers la page de connexion
        // sinon $scope.user contient les données utlisateur
        if (!Auth.isConnected()) {
            $state.go('login');
        } else {
            $scope.user = Auth.getUser();
        }

        // Fonction qui permet de détruire l'utilisateur et de le rediriger vers la page de connexion
        $scope.goLogout = function() {
            Auth.destroyUser();
            $state.go('login');
        };
    }]);