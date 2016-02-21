'use strict';

angular.module('unoApp')
    .service('Auth', function (localStorageService, $http, $q) {
        return {
            getUser: function () {
                return localStorageService.get('user');
            },
            setUser: function (newUser) {
                var deferred = $q.defer();

                $http.put('/rest/auth', {
                    email: newUser.email,
                    password: sha1(newUser.password)
                }).then(function (response) {
                    deferred.resolve(response);
                }, function (error) {
                    deferred.reject(error);
                });

                return deferred.promise;
            },
            connectUser: function (newUser) {
                localStorageService.set('user', newUser);
            },
            isConnected: function () {
                return !!localStorageService.get('user');
            },
            destroyUser: function () {
                localStorageService.remove('user');
            },
            registerUser: function (newUser) {
                var deferred = $q.defer();

                $http.post('/rest/auth/signup', {
                        email: newUser.email,
                        playerName: newUser.name,
                        password: sha1(newUser.password)
                    })
                    .then(function (response) {
                        console.log(response);
                        deferred.resolve(response);
                    }, function (error) {
                        console.log(error);
                        deferred.reject(error);
                    });

                return deferred.promise;
            }
        };
    });