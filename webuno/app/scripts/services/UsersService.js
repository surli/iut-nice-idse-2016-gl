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

      updateRoleUser: function (name, role, callback, callbackError) {
        HttpRequest.send({
          method: 'post',
          url: 'rest/admin/player/'+name,
          headers: {
            token: Auth.getUser().token
          },
          data: {
            rang: role
          }
        }, callback, callbackError);
      },

      updateBanUser: function (name, ban, callback, callbackError) {
        HttpRequest.send({
          method: 'put',
          url: 'rest/admin/player/'+name,
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
