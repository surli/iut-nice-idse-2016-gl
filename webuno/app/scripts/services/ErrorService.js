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
                    if (callback && angular.isFunction(callback)) {
                        callback(response.data);
                    }
                } else {
                    if (callbackError && angular.isFunction(callbackError)) {
                        callbackError();
                    }

                    if (response.status === 404) {
                        // DEV ?
                        //$rootScope.error = '404 NOT FOUND';
                    } else {
                        $rootScope.error = response.data.error;
                    }
                }
            }
        };
    });