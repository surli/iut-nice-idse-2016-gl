'use strict';

angular.module('unoApp')
    /**
     * Service ErrorService
     * Gère toutes les erreurs de l'application et factorise le code de celles-ci
     */
    .service('ErrorService', function ($rootScope) {
        return {
            test: function(response, callback, callbackError) {
                // Si le statut est 200 alors j'execute la callback
                // sinon j'affiche l'erreur
                if (response.status === 200) {
                    callback(response.data);
                } else {
                    callback(callbackError);
                    $rootScope.error = response.data.error;
                }
            }
        };
    });