'use strict';

angular.module('unoApp')
    .service('HttpRequest', function ($http, $q) {
        return {
            send: function(req) {
                var deferred = $q.defer();

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