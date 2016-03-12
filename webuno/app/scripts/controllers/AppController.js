'use strict';

angular.module('unoApp')
    /**
     * Contrôleur global AppController de la route /app
     * Gère l'ensemble de l'application une fois un utilisateur connecté
     */
    .controller('AppController', ['$rootScope', '$scope', '$state', '$translate', 'Auth', function ($rootScope, $scope, $state, $translate, Auth) {
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

        $rootScope.lang = $translate.use();
        // Fonction qui permet de changer la langue
        $scope.changeLanguage = function (langKey) {
            // Utilisation de la dépendance $translate pour changer la langue de l'app
            $rootScope.lang = langKey;
            $translate.use(langKey);
        };
    }]);