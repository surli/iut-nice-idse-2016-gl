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

                $http.get('/rest/game/' + gameName, {
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

            // renvoie toutes les parties en cours
            getAllGames: function () {
                var deferred = $q.defer();

                $http.get('/rest/game', {
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

            // renvoie la main d'un joueur
            getUserHand: function (gameName) {
                var deferred = $q.defer();

                $http.get('/rest/game/' + gameName + '/' + Auth.getUser().name, {
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
            },

            // pioche une carte
            drawCard: function (gameName) {
                var deferred = $q.defer();

                $http.post('/rest/game/' + gameName + '/' + Auth.getUser().name, {}, {
                        headers: {
                            token: Auth.getUser().token
                        }
                    })
                    .then(function (response) {
                        console.log(response);
                        deferred.resolve(response);
                    }, function (error) {
                        console.error(error);
                        deferred.reject(error);
                    });

                return deferred.promise;
            },

            // jouer une carte
            playCard: function (gameName, carte) {
                var deferred = $q.defer();

                $http.put('/rest/game/' + gameName + '/' + Auth.getUser().name, {
                        value: carte.number,
                        color: carte.family
                    }, {
                        headers: {
                            token: Auth.getUser().token
                        }
                    })
                    .then(function (response) {
                        console.log(response);
                        deferred.resolve(response);
                    }, function (error) {
                        deferred.reject(error);
                        console.error(error);
                    });

                return deferred.promise;
            },

            // retourne le joueur devant jouer
            getCurrentPlayer: function (gameName) {
                var deferred = $q.defer();

                $http.get('/rest/game/' + gameName + '/command', {
                        headers: {
                            token: Auth.getUser().token
                        }
                    })
                    .then(function (response) {
                        deferred.resolve(response);
                    }, function (error) {
                        console.error(error);
                        deferred.reject(error);
                    });

                return deferred.promise;
            }

        };
    });
