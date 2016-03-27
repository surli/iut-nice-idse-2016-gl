'use strict';

angular.module('unoApp')
    /**
     * Service HttpRequest
     * Gère toutes les requêtes http de l'application et factorise le code de celles-ci
     *
     * Prend en paramètre un objet contenant la configuration de la requête :
     *
     * {
     *     method: 'put',
     *     url: 'rest/game/' + gameName + '/' + Auth.getUser().name,
     *     headers: {
     *         token: Auth.getUser().token
     *     },
     *     data: {
     *         value: carte.number,
     *         color: carte.family
     *     }
     * }
     *
     */
    .service('HttpRequest', function (ErrorService, $http, $rootScope) {
        return {
            send: function(req, callback, callbackError) {
                // Execute une requête http par rapport à la configuration définie
                $http(req).then(function(response) {
                    // Utilisation du service ErrorService pour tester la requete
                    // Si elle est 200 alors on execute la callback, sinon on affiche une erreur
                    ErrorService.test(response, callback, callbackError);
                }, function(response) {
                    if (callbackError && angular.isFunction(callbackError)) {
                        callbackError();
                    }

                    if (response.status === 404) {
                        // DEV ?
                        //$rootScope.error = '404 NOT FOUND';
                    } else {
                        //$rootScope.error = response.data.error;
                    }
                });
            }
        };
    });