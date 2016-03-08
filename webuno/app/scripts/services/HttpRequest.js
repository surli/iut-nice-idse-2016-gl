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
    .service('HttpRequest', function ($http, $q) {
        return {
            send: function(req) {
                var deferred = $q.defer();

                // Execute une requête http par rapport à la configuration définie
                $http(req).then(function(response) {
                    console.log(req.url + ': ', response);
                    deferred.resolve(response);
                }, function(error) {
                    console.error(req.url + ': ', error);
                    deferred.resolve(error);
                });

                return deferred.promise;
            }
        };
    });