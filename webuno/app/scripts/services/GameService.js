'use strict';

angular.module('unoApp')
    .service('Game', function (Auth, $http, $q) {
        return {

            // créer une partie
            createGame: function (game, nbPlayers) {
                var deferred = $q.defer();

                $http.post('/rest/game', {
                        game: game,
                        player: Auth.getUser().name,
                        numberplayers: nbPlayers
                    }, {
                        headers: {
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
            },

            // renvoie les data de la partie
            getGame: function (gameName) {
                var deferred = $q.defer();
                $http.get('/rest/game/' + gameName)
                    .then(function (response) {
                        console.log(response);
                        deferred.resolve(response);
                    }, function (error) {
                        console.log(error);
                        deferred.reject(error);
                    });
                return deferred.promise;
            },

            // renvoie toutes les parties en cours
            getAllGames: function () {
                var deferred = $q.defer();
                $http.get('/rest/game')
                    .then(function (response) {
                        console.log(response);
                        deferred.resolve(response);
                    }, function (error) {
                        console.log(error);
                        deferred.reject(error);
                    });
                return deferred.promise;
            },

            // renvoie la main d'un joueur
            getUserHand: function (gameName, userName) {
                var deferred = $q.defer();

                $http.get('/rest/game/' + gameName + '/' + userName)
                    .then(function (response) {
                        console.log(response);
                        deferred.resolve(response);
                    }, function (error) {
                        console.log(error);
                        deferred.reject(error);
                    });

                return deferred.promise;
            },

            // ajoute un joueur à une partie
            joinGame: function (gameName) {
                var deferred = $q.defer();

                $http.put('/rest/game/' + gameName, {
                        playerName: Auth.getUser().name
                    }, {
                        headers: {
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
