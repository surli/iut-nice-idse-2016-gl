'use strict';

angular.module('unoApp')
    .service('Auth', function (localStorageService, HttpRequest) {
        return {
            getUser: function () {
                return localStorageService.get('user');
            },
            setUser: function (newUser) {
                return HttpRequest.send({
                    method: 'put',
                    url: 'rest/auth',
                    data: {
                        email: newUser.email,
                        password: CryptoJS.SHA1(newUser.password)
                    }
                });
            },
            setUserGuess: function (playername) {
                return HttpRequest.send({
                    method: 'post',
                    url: 'rest/auth',
                    data: {
                        playername: playername
                    }
                });
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
                return HttpRequest.send({
                    method: 'post',
                    url: 'rest/auth/signup',
                    data: {
                        email: newUser.email,
                        playerName: newUser.name,
                        password: CryptoJS.SHA1(newUser.password)
                    }
                });
            }
        };
    });
