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
          url: 'rest/admin/users',
          headers: {
            token: Auth.getUser().token
          }
        }, callback, callbackError);
      }

    };

  });
