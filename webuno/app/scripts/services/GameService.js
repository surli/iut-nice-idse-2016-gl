'use strict';

angular.module('unoApp')
    .service('Game', function (Auth, HttpRequest) {
        return {
            // créer une partie
            createGame: function (game, nbPlayers) {
                return HttpRequest.send({
                    method: 'post',
                    url: '/rest/game',
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: {
                        game: game,
                        player: Auth.getUser().name,
                        numberplayers: nbPlayers
                    }
                });
            },

            // renvoie les data de la partie
            getGame: function (gameName) {
                return HttpRequest.send({
                    method: 'get',
                    url: '/rest/game/' + gameName,
                    headers: {
                        token: Auth.getUser().token
                    }
                });
            },

            // renvoie toutes les parties en cours
            getAllGames: function () {
                return HttpRequest.send({
                    method: 'get',
                    url: '/rest/game',
                    headers: {
                        token: Auth.getUser().token
                    }
                });
            },

            // renvoie la main d'un joueur
            getUserHand: function (gameName) {
                return HttpRequest.send({
                    method: 'get',
                    url: '/rest/game/' + gameName + '/' + Auth.getUser().name,
                    headers: {
                        token: Auth.getUser().token
                    }
                });
            },

            // ajoute un joueur à une partie
            joinGame: function (gameName) {
                return HttpRequest.send({
                    method: 'put',
                    url: '/rest/game/' + gameName,
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: {
                        playerName: Auth.getUser().name
                    }
                });
            },

            // pioche une carte
            drawCard: function (gameName) {
                return HttpRequest.send({
                    method: 'post',
                    url: '/rest/game/' + gameName + '/' + Auth.getUser().name,
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: {}
                });
            },

            // jouer une carte
            playCard: function (gameName, carte) {
                return HttpRequest.send({
                    method: 'put',
                    url: '/rest/game/' + gameName + '/' + Auth.getUser().name,
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: {
                        value: carte.number,
                        color: carte.family
                    }
                });
            },

            // retourne le joueur devant jouer
            getCurrentPlayer: function (gameName) {
                return HttpRequest.send({
                    method: 'get',
                    url: '/rest/game/' + gameName + '/command',
                    headers: {
                        token: Auth.getUser().token
                    }
                });
            }
        };
    });
