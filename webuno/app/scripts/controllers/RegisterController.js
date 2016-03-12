'use strict';

angular.module('unoApp')
    /**
     * Contrôleur RegisterController de la route /register
     * Gère l'inscription/enregistrement d'un nouvel utilisateur
     */
    .controller('RegisterController', ['$rootScope', '$scope', '$state', '$translate', 'Auth', function ($rootScope, $scope, $state, $translate, Auth) {
        // Utilisation du service Auth qui retourne si l'utilisateur est connecté
        // Si un utilisateur est déjà authentifié
        // alors il est redirigé vers la page d'accueil de l'application
        if (Auth.isConnected()) {
            $state.go('app.home');
        }

        $scope.newUser  = {};
        $scope.error    = '';

        // Fonction qui permet l'inscription d'un nouvel utilisateur
        // Cette fonction est appelée à l'envoi du formulaire html
        $scope.goRegister = function () {
            // Utilisation du service Auth qui permet d'inscrire un nouvel utilisateur
            Auth.registerUser($scope.newUser)
                .then(function (response) {
                    // Si les informations données sont incorrecte ou que l'utilisateur existe déjà
                    // alors un message d'erreur est affiché
                    // sinon l'utilisateur est connecté et redirigé vers la page d'accueil de l'application
                    if (response.data.error) {
                        $scope.error = response.data.error;
                    } else {
                        response.data.name = $scope.newUser.name;
                        // Utilisation du service Auth pour setter les informations utilisateur dans la session
                        Auth.connectUser(response.data);
                        $state.go('app.home');
                    }
                });
        };

        $rootScope.lang = $translate.use();
        // Fonction qui permet de changer la langue
        $scope.changeLanguage = function (langKey) {
            // Utilisation de la dépendance $translate pour changer la langue de l'app
            $rootScope.lang = langKey;
            $translate.use(langKey);
        };
    }]);