'use strict';

angular.module('unoApp')
    .service('Auth', function(localStorageService, $http, $q) {
        return {
            getUser: function() {
                return localStorageService.get('user');
            },
            setUser: function(newUser) {
                var deferred = $q.defer();
                $http.post('/rest/auth', {
                    playername: newUser
                }).then(function(response) {
                    deferred.resolve(response.data);
                }, function(error) {
                    deferred.reject(error);
                });

                return deferred.promise;
            },
            connectUser: function(newUser) {
                localStorageService.set('user', newUser);
            },
            isConnected: function() {
                return !!localStorageService.get('user');
            },
            destroyUser: function() {
                localStorageService.remove('user');
            }
        };
    });