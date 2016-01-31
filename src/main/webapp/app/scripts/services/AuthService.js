'use strict';

angular.module('unoApp')
    .service('Auth', function(localStorageService) {
        return {
            getUser: function() {
                return localStorageService.get('user');
            },
            setUser: function(newUser) {
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