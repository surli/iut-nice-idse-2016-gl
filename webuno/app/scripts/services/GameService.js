'use strict';

angular.module('unoApp')
    .service('Game', function (Auth, HttpRequest) {
        return {

            /**
             * Permet la création d'une partie
             *
             * @param game
             * @param nbPlayers
             * @returns {*}
             */
            createGame: function (game, nbPlayers) {
                return HttpRequest.send({
                    method: 'post',
                    url: 'rest/game',
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

            /**
             * Retourne l'état de la partie passée en paramètre
             *
             * @param gameName
             * @returns {*}
             */
            getGame: function (gameName) {
                return HttpRequest.send({
                    method: 'get',
                    url: 'rest/game/' + gameName,
                    headers: {
                        token: Auth.getUser().token
                    }
                });
            },

            /**
             * Retourne la liste de toutes les parties
             *
             * @returns {*}
             */
            getAllGames: function () {
                return HttpRequest.send({
                    method: 'get',
                    url: 'rest/game',
                    headers: {
                        token: Auth.getUser().token
                    }
                });
            },

            /**
             * Retourne la main du joueur connecté
             *
             * @param gameName
             * @returns {*}
             */
            getUserHand: function (gameName) {
                return HttpRequest.send({
                    method: 'get',
                    url: 'rest/game/' + gameName + '/' + Auth.getUser().name,
                    headers: {
                        token: Auth.getUser().token
                    }
                });
            },

            /**
             * Permet de rejoindre une partie
             *
             * @param gameName
             * @returns {*}
             */
            joinGame: function (gameName) {
                return HttpRequest.send({
                    method: 'put',
                    url: 'rest/game/' + gameName,
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: {
                        playerName: Auth.getUser().name
                    }
                });
            },

            /**
             * Permet de démarrer une partie
             *
             * @param gameName
             * @returns {*}
             */
            startGame: function (gameName) {
                return HttpRequest.send({
                    method: 'put',
                    url: 'rest/game/' + gameName + '/command',
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: {
                        playerName: Auth.getUser().name
                    }
                });
            },

            /**
             * Permet de piocher une carte
             *
             * @param gameName
             * @returns {*}
             */
            drawCard: function (gameName) {
                return HttpRequest.send({
                    method: 'post',
                    url: 'rest/game/' + gameName + '/' + Auth.getUser().name,
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: {}
                });
            },

            /**
             * Permet de jouer une carte
             *
             * @param gameName
             * @param carte
             * @returns {*}
             */
            playCard: function (gameName, carte) {
                var data = {
                    value: carte.number,
                    color: carte.family
                };

                if (carte.setcolor) {
                    data.setcolor = carte.setcolor;
                }

                return HttpRequest.send({
                    method: 'put',
                    url: 'rest/game/' + gameName + '/' + Auth.getUser().name,
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: data
                });
            },

            /**
             * Retourne le nom du joueur devant jouer
             *
             * @param gameName
             * @returns {*}
             */
            getCurrentPlayer: function (gameName) {
                return HttpRequest.send({
                    method: 'get',
                    url: 'rest/game/' + gameName + '/command',
                    headers: {
                        token: Auth.getUser().token
                    }
                });
            },

            /**
             * Permet de quitter la room passée en paramètre
             *
             * @param gameName
             * @returns {*}
             */
            quitRoom: function (gameName) {
                return HttpRequest.send({
                    method: 'delete',
                    url: 'rest/game/' + gameName + '/' + Auth.getUser().name,
                    headers: {
                        token: Auth.getUser().token
                    }
                });
            }
        };
    });
