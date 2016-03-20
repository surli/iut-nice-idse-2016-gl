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
             * Test si l'utilisateur est un Guest
             *
             * @returns {boolean}
             */
            isGuest: function () {
                return this.getUser().rang === 2;
            },

            /**
             * Test si l'utilisateur est un Register
             *
             * @returns {boolean}
             */
            isRegister: function () {
                return this.getUser().rang === 3;
            },

            /**
             * Test si l'utilisateur est un Admin
             *
             * @returns {boolean}
             */
            isAdmin: function () {
                return this.getUser().rang === 4;
            },

            /**
             * Supprime l'utilisateur en session
             */
            destroyUser: function () {
                localStorageService.remove('user');
            },

            /**
             * Permet la connexion d'un utilisateur avec pseudo et mot de passe
             *
             * @param newUser
             * @param callback
             * @param callbackError
             */
            setUser: function (newUser, callback, callbackError) {
                HttpRequest.send({
                    method: 'put',
                    url: 'rest/auth',
                    data: {
                        email: newUser.email,
                        password: CryptoJS.SHA1(newUser.password)
                    }
                }, callback, callbackError);
            },

            /**
             * Permet la connexion d'un utilisateur en tant qu'invité
             *
             * @param playername
             * @param callback
             * @param callbackError
             */
            setUserGuess: function (playername, callback, callbackError) {
                HttpRequest.send({
                    method: 'post',
                    url: 'rest/auth',
                    data: {
                        playername: playername
                    }
                }, callback, callbackError);
            },

            /**
             * Permet de déconnecter l'utilisateur courant
             *
             * @param callback
             * @param callbackError
             */
            decoUser: function(callback, callbackError) {
                HttpRequest.send({
                    method: 'delete',
                    url: 'rest/auth',
                    headers: {
                        token: this.getUser().token
                    }
                }, callback, callbackError);
            },

            /**
             * Permet l'inscription d'un utilisateur
             *
             * @param newUser
             * @param callback
             * @param callbackError
             */
            registerUser: function (newUser, callback, callbackError) {
                HttpRequest.send({
                    method: 'post',
                    url: 'rest/auth/signup',
                    data: {
                        email: newUser.email,
                        playerName: newUser.name,
                        password: CryptoJS.SHA1(newUser.password)
                    }
                }, callback, callbackError);
            }
        };
    });
