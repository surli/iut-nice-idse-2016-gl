'use strict';

angular.module('unoApp')
    .service('Auth', function (localStorageService, $http, $q) {
        return {
            getUser: function () {
                return localStorageService.get('user');
            },
            setUser: function (newUser) {
                var deferred = $q.defer();
                $http.post('/rest/auth', {
                    playername: newUser
                }).then(function (response) {
                    deferred.resolve(response.data);
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
            registerUser: function (email, name, password) {
                var deferred = $q.defer();
                // TODO voir la route pour le register et la méthode
                $http.put('/rest/game/' + gameName, {
                        email: email,
                        name: name,
                        password: password
                    }, {
                        // L'API REST demande un token dans le header
                        headers: {
                            // Donc dans le header on ajoute le token venant de l'utilisateur
                            token: Auth.getUser().token
                        }
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