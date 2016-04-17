'use strict';

angular.module('unoApp')
    .service('Users', function (Auth, HttpRequest) {
        return {

            /**
             * Retourne la liste de tous les utilisateurs
             *
             * @param callback
             * @param callbackError
             */
            getAllUsers: function (callback, callbackError) {
                HttpRequest.send({
                    method: 'get',
                    url: 'rest/admin/player',
                    headers: {
                        token: Auth.getUser().token
                    }
                }, callback, callbackError);
            },

            /**
             * Permet de mettre à jour le role de l'utilisateur passé en paramètre
             *
             * @param name
             * @param role
             * @param callback
             * @param callbackError
             */
            updateRoleUser: function (name, role, callback, callbackError) {
                HttpRequest.send({
                    method: 'post',
                    url: 'rest/admin/player/' + name,
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: {
                        rang: role
                    }
                }, callback, callbackError);
            },

            /**
             * Permet de bannir ou non un utilisateur passé en paramètre
             *
             * @param name
             * @param ban
             * @param callback
             * @param callbackError
             */
            updateBanUser: function (name, ban, callback, callbackError) {
                HttpRequest.send({
                    method: 'put',
                    url: 'rest/admin/player/' + name,
                    headers: {
                        token: Auth.getUser().token
                    },
                    data: {
                        ban: ban
                    }
                }, callback, callbackError);
            }
        };
    });
