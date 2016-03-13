'use strict';

angular.module('unoApp')
    /**
     * Service Auth
     * Gère et centralise l'authentification (connexion/inscription) utilisateur
     */
    .service('Auth', function (localStorageService, HttpRequest) {
        return {
            /**
             * Retourne l'utilisateur en session
             */
            getUser: function () {
                return localStorageService.get('user');
            },
            /**
             * Permet la connexion d'un utilisateur avec pseudo et mot de passe
             *
             * @param newUser
             * @returns {*}
             */
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
            /**
             * Permet la connexion d'un utilisateur en tant qu'invité
             *
             * @param playername
             * @returns {*}
             */
            setUserGuess: function (playername) {
                return HttpRequest.send({
                    method: 'post',
                    url: 'rest/auth',
                    data: {
                        playername: playername
                    }
                });
            },
            /**
             * Insert un utilisateur dans la session
             *
             * @param newUser
             */
            connectUser: function (newUser) {
                localStorageService.set('user', newUser);
            },
            /**
             * Retourne si l'utilisateur est connecté ou non
             *
             * @returns {boolean}
             */
            isConnected: function () {
                return !!localStorageService.get('user');
            },
            /**
             * Supprime l'utilisateur en session
             */
            destroyUser: function () {
                localStorageService.remove('user');
            },

            /**
             * Permet de déconnecter l'utilisateur courant
             *
             * @returns {*}
             */
            decoUser: function() {
                return HttpRequest.send({
                    method: 'delete',
                    url: 'rest/auth',
                    headers: {
                        token: this.getUser().token
                    }
                });
            },

            /**
             * Permet l'inscription d'un utilisateur
             *
             * @param newUser
             * @returns {*}
             */
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
